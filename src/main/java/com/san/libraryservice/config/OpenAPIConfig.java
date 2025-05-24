package com.san.libraryservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.san.libraryservice.constant.ConfigConstants.*;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(LIBRARY_MANAGEMENT_API)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION));
    }
}
