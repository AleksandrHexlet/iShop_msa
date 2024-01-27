package com.sprng.feedback.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ClientCustomerAndTraderService {
    WebClient webClient;

    public ClientCustomerAndTraderService() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("http://localhost:8081/api/users").build();
    }


    public Mono<Boolean> existsByCustomerAndProductTrader(int customerId, int productTraderId, String tokenValue) {
        return webClient.get()
                .uri("/exists/customer/{customerId}/trader/{productTraderId}",
                        customerId, productTraderId)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenValue))
                .retrieve()
                .bodyToMono(Boolean.class)
                .timeout(Duration.ofMillis(100));
//            .onErrorReturn(false);
    }


}
