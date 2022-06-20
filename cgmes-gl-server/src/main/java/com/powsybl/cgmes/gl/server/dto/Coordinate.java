/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Coordinate {

    private  double lat;

    private  double lon;
}
