package com.powsybl.cases.datasource;

import com.powsybl.commons.PowsyblException;
import com.powsybl.commons.datasource.ReadOnlyDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CaseServerDataSource implements ReadOnlyDataSource {

    public static final  String CASE_API_VERSION = "v1";

    private RestTemplate caseServerRest;
    private String caseServerBaseUri;

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    private String caseName;

    public CaseServerDataSource(@Value("${case-server.base.url}") String caseServerBaseUri, String caseName) {
        this(caseServerBaseUri);
        this.caseName = Objects.requireNonNull(caseName);
    }

    public CaseServerDataSource(@Value("${case-server.base.url}") String caseServerBaseUri) {
        this.caseServerBaseUri = Objects.requireNonNull(caseServerBaseUri);
        RestTemplateBuilder restTemplateBuilder2 = new RestTemplateBuilder();
        this.caseServerRest = restTemplateBuilder2.build();
        this.caseServerRest.setUriTemplateHandler(new DefaultUriBuilderFactory(caseServerBaseUri));
    }

    @Override
    public String getBaseName() {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("caseName", caseName);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(caseServerBaseUri + "/" + CASE_API_VERSION + "/cases/{caseName}/datasource/baseName")
                .uriVariables(urlParams);
        try {
            ResponseEntity<String> responseEntity = caseServerRest.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    String.class);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw new PowsyblException(" Exception", e);
        }
    }

    @Override
    public boolean exists(String suffix, String ext) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("caseName", caseName);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(caseServerBaseUri + "/" + CASE_API_VERSION + "/cases/{caseName}/datasource/exists")
                .uriVariables(urlParams)
                .queryParam("caseName", caseName)
                .queryParam("suffix", suffix)
                .queryParam("ext", ext);
        try {
            ResponseEntity<Boolean> responseEntity = caseServerRest.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    Boolean.class);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw new PowsyblException(" Exception", e);
        }
    }

    @Override
    public boolean exists(String fileName) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("caseName", caseName);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(caseServerBaseUri + "/" + CASE_API_VERSION + "/cases/{caseName}/datasource/exists")
                .uriVariables(urlParams)
                .queryParam("caseName", caseName)
                .queryParam("fileName", fileName);
        try {
            ResponseEntity<Boolean> responseEntity = caseServerRest.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    Boolean.class);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw new PowsyblException(" Exception", e);
        }
    }

    @Override
    public InputStream newInputStream(String suffix, String ext) throws IOException {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("caseName", caseName);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(caseServerBaseUri + "/" + CASE_API_VERSION + "/cases/{caseName}/datasource")
                .uriVariables(urlParams)
                .queryParam("caseName", caseName)
                .queryParam("suffix", suffix)
                .queryParam("ext", ext);
        try {
            ResponseEntity<byte[]> responseEntity = caseServerRest.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    byte[].class);
            return new ByteArrayInputStream(responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            throw new PowsyblException(" Exception", e);
        }
    }

    @Override
    public InputStream newInputStream(String fileName) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("caseName", caseName);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(caseServerBaseUri + "/" + CASE_API_VERSION + "/cases/{caseName}/datasource")
                .uriVariables(urlParams)
                .queryParam("caseName", caseName)
                .queryParam("fileName", fileName);
        try {
            ResponseEntity<byte[]> responseEntity = caseServerRest.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    byte[].class);
            return new ByteArrayInputStream(responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            throw new PowsyblException(" Exception", e);
        }
    }

    @Override
    public Set<String> listNames(String regex) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("caseName", caseName);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(caseServerBaseUri + "/" + CASE_API_VERSION + "/cases/{caseName}/datasource/list")
                .uriVariables(urlParams)
                .queryParam("caseName", caseName)
                .queryParam("regex", regex);
        try {
            ResponseEntity<Set<String>> responseEntity = caseServerRest.exchange(uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Set<String>>() { });
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            throw new PowsyblException(" Exception", e);
        }
    }
}
