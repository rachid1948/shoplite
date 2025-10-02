package com.shoplite.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient inventoryRestClient(@Value("${inventory.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)   // ex: http://localhost:8080
                .build();
    }
}
