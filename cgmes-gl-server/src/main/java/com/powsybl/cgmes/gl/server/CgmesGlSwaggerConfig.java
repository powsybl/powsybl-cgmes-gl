/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.cgmes.gl.server;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chamseddine Benhamed <chamseddine.benhamed at rte-france.com>
 */
@Configuration
public class CgmesGlSwaggerConfig {
    /*@Bean
    public Docket produceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(CgmesGlApplication.class.getPackage().getName()))
                .paths(paths())
                .build();
    }

    // Describe your apis
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CGMES GL API")
                .description("This is the documentation of PowSyBl CGMES GL API")
                .version(CgmesGlController.API_VERSION)
                .build();
    }

    // Only select apis that matches the given Predicates.
    private Predicate<String> paths() {
        // Match all paths except /error
        return Predicates.and(PathSelectors.regex("/" + CgmesGlController.API_VERSION + ".*"), Predicates.not(PathSelectors.regex("/error.*"))::apply);
    }*/
    @Bean
    public OpenAPI creOpenAPI() {
        return new OpenAPI()
             .info(new Info()
                  .title("CGMES GL API")
                  .description("This is the documentation of PowSyBl CGMES GL API")
                  .version(CgmesGlController.API_VERSION));
    }
}
