package com.stars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI starsOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Stars API")
                .description("REST API of the stars application")
                .version("0.0.1-SNAPSHOT")
                .contact(new Contact().name("Stars team")));
    }

}