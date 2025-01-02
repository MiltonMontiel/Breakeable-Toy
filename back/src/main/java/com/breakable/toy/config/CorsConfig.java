package com.breakable.toy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String commonOrigin = "http://localhost:8080";
        registry.addMapping("/products/**")
                .allowedOrigins(commonOrigin)
                .allowedMethods("GET", "POST", "PUT");
    }
}
