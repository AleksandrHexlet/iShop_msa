package com.sprng.orderService.service;


import com.sprng.orderService.config.WebClientService;
import com.sprng.orderService.model.OrderDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class ClientProductService {

    WebClient webClient;

    @Autowired
    public ClientProductService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://PRODUCT/api/product").build();
    }

    public Flux<OrderDataDTO> getCountProduct(List<OrderDataDTO> orderDataDtoList){

    return webClient
                .get()
                .uri("/count/{idList}", orderDataDtoList.stream().
                        map(orderDataDTO -> String.valueOf(orderDataDTO.getIdProduct()))
                        .collect(Collectors.joining(",")))
            .retrieve()
            .bodyToFlux(OrderDataDTO.class);
//            .collectList();
//            .bodyToMono(new ParameterizedTypeReference<List<OrderDataDTO>>() {
            };

    }

