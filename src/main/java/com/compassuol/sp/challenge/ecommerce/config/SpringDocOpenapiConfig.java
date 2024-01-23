package com.compassuol.sp.challenge.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocOpenapiConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
        .info(
            new Info()
                .title("E-commerce API")
                .description("API do e-commerce")
                .version("1.0.0")
                .contact(new Contact().name("NullPointerException Team").email("nullpointerexception@team.com"))
            );
    }
}
