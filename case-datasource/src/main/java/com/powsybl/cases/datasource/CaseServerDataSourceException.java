/** Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cases.datasource;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
public class CaseServerDataSourceException extends RuntimeException {

    public CaseServerDataSourceException(String msg) {
        super(msg);
    }

    public CaseServerDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}


