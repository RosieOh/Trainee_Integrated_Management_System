package com.lms.global.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
//http://localhost:8080/swagger-ui/
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .packagesToScan("com.elasticsearchtest.domain.controller")
                .build();
    }
}