package com.preppilot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                .info(

                        new Info()

                                .title("PrepPilot API")

                                .version("1.0")

                                .description("AI Interview Preparation Platform"));

    }
}
