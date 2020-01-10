/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.services;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
public final class CgmesGlConstants {

    private CgmesGlConstants() {
    }

    static final String GEO_DATA_API_VERSION = "v1";
    static final String CASE_API_VERSION = "v1";

    static final String USERHOME = "user.home";
    static final String TMP_FOLDER = "/tmp-zipped-cases/";

    static final String STORAGE_DIR_NOT_CREATED = "The storage is not initialized";
    static final String FILE_DOESNT_EXIST = "The file requested doesn't exist";
}
