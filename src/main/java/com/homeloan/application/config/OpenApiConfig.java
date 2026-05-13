package com.homeloan.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc OpenAPI: Swagger UI + Bearer JWT scheme for secured endpoints.
 */
@Configuration
public class OpenApiConfig {

    public static final String BEARER_SCHEME = "bearer-jwt";

    @Bean
    public OpenAPI homeLoanOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Home Loan Application API")
                        .description("Monolithic Spring Boot REST API for home loan applications")
                        .version("v1"))
                .components(new Components().addSecuritySchemes(BEARER_SCHEME,
                        new SecurityScheme()
                                .name(BEARER_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
