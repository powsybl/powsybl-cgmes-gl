/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.web.client.RestTemplate;

import java.util.HashSet;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class CgmesGlServiceTest {
    @Mock
    private RestTemplate geoDataServerRest;

    @Mock
    private RestTemplate caseServerRest;

    @InjectMocks
    private CgmesGlService cgmesGlService =  new CgmesGlService("https://localhost:8080", "https://localhost:8085");

    @Test
    public void test() {
        cgmesGlService.getNetwork("testCaseName");
        cgmesGlService.toGeodDataServer("testCaseName", new HashSet<>());
    }
}
