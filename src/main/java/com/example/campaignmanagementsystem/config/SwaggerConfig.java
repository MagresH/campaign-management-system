package com.example.campaignmanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(
                new io.swagger.v3.oas.models.info.Info()
                        .title("Campaign Management System")
                        .description("A simple API for managing campaigns")
                        .version("1.0.0")
        );
    }
}