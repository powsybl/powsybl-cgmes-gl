/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server;

import com.powsybl.cgmes.gl.server.dto.LineGeoData;
import com.powsybl.cgmes.gl.server.dto.SubstationGeoData;
import com.powsybl.geodata.extensions.GLTestUtils;
import com.powsybl.iidm.network.Country;
import com.powsybl.iidm.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@Service
public class CgmesGlService {
    private static final String GEO_DATA_API_VERSION = "v1";

    private static final Logger LOGGER = LoggerFactory.getLogger(CgmesGlService.class);

    private RestTemplate geoDataServerRest;
    private String geoDataServerBaseUri;

    @Autowired
    public CgmesGlService(@Value("${geo-data-server.base.url}") String geoDataServerBaseUri) {
        this.geoDataServerBaseUri = Objects.requireNonNull(geoDataServerBaseUri);
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.geoDataServerRest = restTemplateBuilder.build();
        this.geoDataServerRest.setUriTemplateHandler(new DefaultUriBuilderFactory(geoDataServerBaseUri));
    }

    public void toGeodDataServer(String caseName, Set<Country> countries) {
        Network network = getNetwork(caseName);
        // TO DO
        saveData(new ArrayList<>(), new ArrayList<>());
    }

    private Network getNetwork(String caseName) {
        // TO DO
        // get data from case server, then construct the network

        return GLTestUtils.getNetwork();
    }

    private void saveData(List<SubstationGeoData> substationsGeoData, List<LineGeoData> linesGeoData) {
        // send geographic data to geo data server
        pushSubstations(substationsGeoData);
        pushLines(linesGeoData);
    }

    private void pushSubstations(List<SubstationGeoData> substationsGeoData) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(geoDataServerBaseUri + "/" + GEO_DATA_API_VERSION + "/substations");

        HttpEntity<List<SubstationGeoData>> requestEntity = new HttpEntity<>(substationsGeoData, requestHeaders);

        geoDataServerRest.exchange(uriBuilder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                Void.class);
    }

    private void pushLines(List<LineGeoData> linesGeoData) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(geoDataServerBaseUri + "/" + GEO_DATA_API_VERSION + "/lines");

        HttpEntity<List<LineGeoData>> requestEntity = new HttpEntity<>(linesGeoData, requestHeaders);

        geoDataServerRest.exchange(uriBuilder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                Void.class);
    }
}

