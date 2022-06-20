/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server;

import com.powsybl.ws.commons.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
public class CgmesGlApplication {

    public static void main(String[] args) {
        Utils.initProperties();
        SpringApplication.run(CgmesGlApplication.class, args);
    }
}
