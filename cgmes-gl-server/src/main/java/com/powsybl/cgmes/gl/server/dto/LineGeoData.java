/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.dto;

import com.powsybl.geodata.extensions.Coordinate;
import com.powsybl.geodata.extensions.LinePosition;
import com.powsybl.iidm.network.Country;
import com.powsybl.iidm.network.Line;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class LineGeoData {

    private  String id;

    private Country country1;

    private Country country2;

    private List<Coordinate> coordinates = new ArrayList<>();

    public static LineGeoData fromLinePosition(LinePosition linePosition) {
        Line l = (Line) linePosition.getExtendable();
        Country country1 = l.getTerminal1().getVoltageLevel().getSubstation().getCountry().orElse(null);
        Country country2 = l.getTerminal2().getVoltageLevel().getSubstation().getCountry().orElse(null);

        return new LineGeoData(l.getId(), country1, country2, linePosition.getCoordinates());
    }
}
