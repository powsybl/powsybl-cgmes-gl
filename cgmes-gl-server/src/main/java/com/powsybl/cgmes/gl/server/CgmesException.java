package com.powsybl.cgmes.gl.server;
/* Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
public class CgmesException extends RuntimeException {

    public CgmesException(String msg) {
        super(msg);
    }

    public CgmesException(String message, Throwable cause) {
        super(message, cause);
    }
}

