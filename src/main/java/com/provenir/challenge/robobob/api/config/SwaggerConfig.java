package com.provenir.challenge.robobob.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration details for Swagger/OpenAPI documentation.
 * API documentation can be accessed through Swagger UI.
 */

@Configuration
public class SwaggerConfig {

    @Value("${robobob.api.version}")
    private String apiVersion;

    /**
     * Configures OpenAPI documentation for Robobob API
     *
     * @return OpenAPI configuration bean.
     */
    @Bean
    public OpenAPI robobobOpenAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("Robobob API")
                        .description("API for Robobob Answering Agent for General Questions and Arithmetic Questions.")
                        .version(apiVersion)
                        .contact(new Contact().name("KurinchiMalar").email("kurinchimalar.n@gmail.com")));
    }
}
