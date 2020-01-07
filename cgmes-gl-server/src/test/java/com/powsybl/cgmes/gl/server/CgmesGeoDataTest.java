package com.powsybl.cgmes.gl.server;

import com.powsybl.cgmes.conformity.test.CgmesConformity1Catalog;
import com.powsybl.cgmes.conversion.CgmesImport;
import com.powsybl.cgmes.gl.server.dto.LineGeoData;
import com.powsybl.cgmes.gl.server.dto.SubstationGeoData;
import com.powsybl.cgmes.model.test.TestGridModel;
import com.powsybl.geodata.extensions.LinePosition;
import com.powsybl.geodata.extensions.SubstationPosition;
import com.powsybl.iidm.network.Line;
import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.network.impl.NetworkFactoryImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CgmesGeoDataTest {
    private static final Logger LOG = LoggerFactory.getLogger(CgmesGeoDataTest.class);

    @Test
    public void test() {
        TestGridModel gridModel =  CgmesConformity1Catalog.microGridBaseCaseBE();

        CgmesImport i = new CgmesImport();
        Properties properties = new Properties();
        properties.put("iidm.import.cgmes.post-processors", "cgmesGLImport");
        Network network = i.importData(gridModel.dataSource(), new NetworkFactoryImpl(), properties);

        List<SubstationPosition> substationPositions = network.getSubstationStream()
                .map(s -> (SubstationPosition) s.getExtension(SubstationPosition.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<LinePosition<Line>> linePositions = new ArrayList<>();
        for (Line line : network.getLines()) {
            LinePosition<Line> linePosition = line.getExtension(LinePosition.class);
            if (linePosition != null) {
                linePositions.add(linePosition);
            }
        }

        assertEquals(2, substationPositions.size());
        assertEquals(2, linePositions.size());

        List<LineGeoData> lines = linePositions.stream()
                .map(lp -> LineGeoData.fromLinePosition(lp))
                .collect(Collectors.toList());
        List<SubstationGeoData> substations = substationPositions.stream().map(sp -> SubstationGeoData.fromSubstationPosition(sp)).collect(Collectors.toList());

        assertEquals(2, substations.size());
        assertEquals(2, lines.size());

        assertEquals(51.3251, substations.get(0).getCoordinate().getLat(), 0);
        assertEquals(4.25926, substations.get(0).getCoordinate().getLon(), 0);
    }
}
