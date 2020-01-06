/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.dto;

import com.powsybl.geodata.extensions.Coordinate;
import com.powsybl.iidm.network.Country;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
public class SubstationGeoDataTest {

    @Test
    public void test() {
        SubstationGeoData substationGeoData = new SubstationGeoData("id", Country.FR, new Coordinate(1, 1));

        assertEquals("id", substationGeoData.getId());
        assertEquals(Country.FR, substationGeoData.getCountry());
        assertEquals(1, substationGeoData.getCoordinate().getLat(), 0);
        assertEquals(1, substationGeoData.getCoordinate().getLon(), 0);

        SubstationGeoData.SubstationGeoDataBuilder substationGeoDataBuilder = SubstationGeoData.builder();
        substationGeoDataBuilder.id("testID");
        substationGeoDataBuilder.country(Country.FR);
        substationGeoDataBuilder.coordinate(new Coordinate(3, 4));
        assertEquals("SubstationGeoData.SubstationGeoDataBuilder(id=testID, country=FR, coordinate=Coordinate(lat=3.0, lon=4.0))", substationGeoDataBuilder.toString());
        assertEquals("SubstationGeoData(id=testID, country=FR, coordinate=Coordinate(lat=3.0, lon=4.0))", substationGeoDataBuilder.build().toString());

    }
}
