package com.sprng.product.service;

import com.sprng.product.config.ServiceToServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class ClientCategoryService {
   private WebClient webClient;

   @Autowired
    public ClientCategoryService(WebClient webClient) {
        this.webClient = webClient.mutate().baseUrl("http://localhost:8083/api/category").build();
    }

    public Mono<Boolean> existsCategoryByID(int categoryID){
     return webClient.get()
              .uri("/exists/id/{categoryID}",categoryID)
              .retrieve()
              .bodyToMono(Boolean.class)
              .timeout(Duration.of(1, ChronoUnit.SECONDS))
              .onErrorReturn(false);
    }
}
