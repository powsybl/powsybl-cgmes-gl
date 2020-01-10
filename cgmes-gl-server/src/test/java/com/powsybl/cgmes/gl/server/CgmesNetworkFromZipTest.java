package com.powsybl.cgmes.gl.server;
 /* Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import com.powsybl.cgmes.conversion.CgmesImport;
import com.powsybl.cgmes.gl.server.dto.LineGeoData;
import com.powsybl.cgmes.gl.server.dto.SubstationGeoData;
import com.powsybl.commons.datasource.DataSource;
import com.powsybl.commons.datasource.ZipFileDataSource;
import com.powsybl.geodata.extensions.LinePosition;
import com.powsybl.geodata.extensions.SubstationPosition;
import com.powsybl.iidm.network.Country;
import com.powsybl.iidm.network.Line;
import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.network.impl.NetworkFactoryImpl;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
public class CgmesNetworkFromZipTest {

    @Test
    public void test() {

        Path path = Paths.get("src/test/ressources/");
        DataSource ds = new ZipFileDataSource(path, "CGMES_v2.4.15_MicroGridTestConfiguration_BC_BE_v2");
        assertNotNull(ds);

        CgmesImport importer = new CgmesImport();
        Properties properties = new Properties();
        properties.put("iidm.import.cgmes.post-processors", "cgmesGLImport");
        Network network = importer.importData(ds, new NetworkFactoryImpl(), properties);
        assertNotNull(network);

        checkExtensions(network, new HashSet<>());
    }

    public static void checkExtensions(Network network, Set<Country> countries) {
        List<SubstationPosition> substationPositions = network.getSubstationStream()
                .map(s -> (SubstationPosition) s.getExtension(SubstationPosition.class))
                .filter(Objects::nonNull)
                .filter(s -> countries.isEmpty() || countries.contains(s.getExtendable().getCountry()))
                .collect(Collectors.toList());

        List<LinePosition<Line>> linePositions = new ArrayList<>();
        Country country1;
        Country country2;
        for (Line line : network.getLines()) {
            LinePosition<Line> linePosition = line.getExtension(LinePosition.class);
            country1 = line.getTerminal1().getVoltageLevel().getSubstation().getCountry().orElse(null);
            country2 = line.getTerminal2().getVoltageLevel().getSubstation().getCountry().orElse(null);
            if (linePosition != null && (countries.isEmpty() || countries.contains(country1) || countries.contains(country2))) {
                linePositions.add(linePosition);
            }
        }

        List<LineGeoData> lines = linePositions.stream().map(lp -> LineGeoData.fromLinePosition(lp)).collect(Collectors.toList());
        List<SubstationGeoData> substations = substationPositions.stream().map(sp -> SubstationGeoData.fromSubstationPosition(sp)).collect(Collectors.toList());

        assertEquals(2, lines.size());
        assertEquals(2, substations.size());
    }
}

