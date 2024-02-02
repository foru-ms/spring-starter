package com.example.forum.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${api.base-url}")
    private String baseUrl;
    @Value("${api.api-keys}")
    private String apiKey;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("x-api-key", apiKey)
                .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build());
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }
}
