/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.services;

import com.powsybl.iidm.network.Network;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
    private CgmesGlService cgmesGlService =  new CgmesGlService("https://localhost:8087", "https://localhost:8085");

    @Test
    public void test() throws Exception {
        // InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("CGMES_v2.4.15_MicroGridTestConfiguration_BC_BE_v2.zip"))
        Path path = Paths.get("src/test/ressources/CGMES_v2.4.15_MicroGridTestConfiguration_BC_BE_v2.zip");
        InputStream inputStream = new FileInputStream(path.toString());
        assertNotNull(inputStream);

        byte[] downloadedBytes = IOUtils.toByteArray(inputStream);
        assertNotNull(downloadedBytes);

        given(caseServerRest.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .willReturn(new ResponseEntity<>(downloadedBytes, HttpStatus.OK));

        Network network = cgmesGlService.getNetwork("CGMES_v2.4.15_MicroGridTestConfiguration_BC_BE_v2.zip");
        assertNotNull(network);

        cgmesGlService.toGeodDataServer("CGMES_v2.4.15_MicroGridTestConfiguration_BC_BE_v2.zip", new HashSet<>());
    }
}
