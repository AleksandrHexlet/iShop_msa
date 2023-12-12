package com.sprng.product.service;

import com.sprng.library.entity.ProductTrader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class ClientUserService {
   private WebClient webClient;

   @Autowired
    public ClientUserService(WebClient webClient) {
        this.webClient = webClient.mutate().baseUrl("http://localhost:8081/api/users").build();
    }

    public Mono<Boolean> existsByIdAndUsernameAndRoleType (int id, String username,
                                                           String role, String token){
     return webClient.get()
               .uri("/exists/id/{id}/username/{username}/role/{role}",id,username,role)
               .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
               .retrieve()
               .bodyToMono(Boolean.class)
               .timeout(Duration.of(1, ChronoUnit.SECONDS))
               .onErrorReturn(false);
   };


    public Mono<ProductTrader> getAllTraderById (String id){
        return webClient.get()
                .uri("/get/traders?id=" + id)
                .retrieve()
                .bodyToMono(ProductTrader.class)
                .timeout(Duration.of(1, ChronoUnit.SECONDS));
    }

}
