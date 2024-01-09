package com.sprng.product.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientCalculationCostDeliveryService {
    WebClient webClient;

    @Autowired
    public ClientCalculationCostDeliveryService(WebClient webClient) {
        this.webClient = webClient.mutate().baseUrl("http://localhost:8086/api/delivery").build();
    }

    public Mono<Map<String,Integer>> getDistanceToStorage(String deliveryCity){
     return webClient
                .get()
                .uri((uriBuilder)-> uriBuilder.path("distance")
                        .queryParam("deliveryCity",deliveryCity)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String,Integer>>() {});
    }
}
