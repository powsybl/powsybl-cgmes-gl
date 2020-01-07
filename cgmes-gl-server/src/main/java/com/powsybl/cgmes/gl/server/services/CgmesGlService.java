/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server.services;

import com.google.common.collect.ImmutableList;
import com.powsybl.cgmes.gl.server.dto.LineGeoData;
import com.powsybl.cgmes.gl.server.dto.SubstationGeoData;
import com.powsybl.geodata.extensions.GLTestUtils;
import com.powsybl.geodata.extensions.LinePosition;
import com.powsybl.geodata.extensions.SubstationPosition;
import com.powsybl.iidm.network.Country;
import com.powsybl.iidm.network.Line;
import com.powsybl.iidm.network.Network;
import com.powsybl.iidm.network.Substation;
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

import java.util.*;
import java.util.stream.Collectors;

import static com.powsybl.geodata.extensions.GLTestUtils.*;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@Service
public class CgmesGlService {
    private static final String GEO_DATA_API_VERSION = "v1";

    private static final Logger LOGGER = LoggerFactory.getLogger(CgmesGlService.class);

    private RestTemplate geoDataServerRest;
    private String geoDataServerBaseUri;
    private RestTemplate caseServerRest;
    private String caseServerBaseUri;

    @Autowired
    public CgmesGlService(@Value("${geo-data-server.base.url}") String geoDataServerBaseUri, @Value("${case-server.base.url}") String caseServerBaseUri) {
        this.geoDataServerBaseUri = Objects.requireNonNull(geoDataServerBaseUri);
        this.caseServerBaseUri = Objects.requireNonNull(caseServerBaseUri);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.geoDataServerRest = restTemplateBuilder.build();
        this.geoDataServerRest.setUriTemplateHandler(new DefaultUriBuilderFactory(geoDataServerBaseUri));

        RestTemplateBuilder restTemplateBuilder2 = new RestTemplateBuilder();
        this.caseServerRest = restTemplateBuilder2.build();
        this.caseServerRest.setUriTemplateHandler(new DefaultUriBuilderFactory(caseServerBaseUri));
    }

    public void toGeodDataServer(String caseName, Set<Country> countries) {
        Network network = getNetwork(caseName);

        List<SubstationPosition> substationPositions = network.getSubstationStream()
                .map(s -> (SubstationPosition) s.getExtension(SubstationPosition.class))
                .filter(Objects::nonNull)
                .filter(s -> countries.isEmpty() || countries.contains(s.getExtendable().getCountry()))
                .collect(Collectors.toList());

        List<LinePosition<Line>> linePositions = new ArrayList<>();
        Country country1;
        Country country2;
        for (Line line : network.getLines()) {
            LinePosition<Line> linePosition = line.getExtension(LinePosition.class);
            country1 = line.getTerminal1().getVoltageLevel().getSubstation().getCountry().orElse(null);
            country2 = line.getTerminal2().getVoltageLevel().getSubstation().getCountry().orElse(null);
            if (linePosition != null && (countries.isEmpty() || countries.contains(country1) || countries.contains(country2))) {
                linePositions.add(linePosition);
            }
        }

        List<LineGeoData> lines = linePositions.stream().map(lp -> LineGeoData.fromLinePosition(lp)).collect(Collectors.toList());
        List<SubstationGeoData> substations = substationPositions.stream().map(sp -> SubstationGeoData.fromSubstationPosition(sp)).collect(Collectors.toList());
        saveData(substations, lines);
    }

    Network getNetwork(String caseName) {
        // get data from case server, then construct the network
        Network network =  GLTestUtils.getNetwork();
        Substation substation1 = network.getSubstation("Substation1");
        SubstationPosition substationPosition1 = new SubstationPosition(substation1, SUBSTATION_1);
        substation1.addExtension(SubstationPosition.class, substationPosition1);

        Substation substation2 = network.getSubstation("Substation2");
        SubstationPosition substationPosition2 = new SubstationPosition(substation2, SUBSTATION_2);
        substation2.addExtension(SubstationPosition.class, substationPosition2);

        Line line = network.getLine("Line");
        line.addExtension(LinePosition.class, new LinePosition<>(line, ImmutableList.of(SUBSTATION_1, LINE_1, LINE_2, SUBSTATION_2)));

        return network;
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

