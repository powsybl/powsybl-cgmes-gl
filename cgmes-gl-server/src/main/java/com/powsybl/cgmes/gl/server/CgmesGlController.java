package com.powsybl.cgmes.gl.server;
/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import com.powsybl.cgmes.gl.server.services.CgmesGlService;
import com.powsybl.iidm.network.Country;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@RestController
@RequestMapping(value = "/" + CgmesGlController.API_VERSION)
@Api(value = "Cgmes gl")
@ComponentScan(basePackageClasses = {CgmesGlController.class})
public class CgmesGlController {

    static final String API_VERSION = "v1";

    @Autowired
    private CgmesGlService cgmesGlService;

    private static Set<Country> toCountrySet(List<String> countries) {
        return countries != null ? countries.stream().map(Country::valueOf).collect(Collectors.toSet()) : Collections.emptySet();
    }

    @PostMapping(value = "/{caseName}/to-geo-data")
    @ApiOperation(value = "extract geographic data from a cgmes case")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "cgmes gl profiles were analysed and sent to geo-data service")})
    public void togeodata(@PathVariable String caseName,
                          @RequestParam(required = false) List<String> countries) {
        Set<Country> countrySet = toCountrySet(countries);
        cgmesGlService.toGeodDataServer(caseName, countrySet);
    }
}


