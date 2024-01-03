package com.sprng.distanceToNeighboursStorage.service;

import com.sprng.distanceToNeighboursStorage.model.DistanceApiResponse;
import com.sprng.distanceToNeighboursStorage.repository.CityStorageRepository;
import com.sprng.distanceToNeighboursStorage.repository.DistanceApiResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistanceApiService {
    private CityStorageRepository cityStorageRepository;
    private DistanceApiResponseRepository distanceApiResponseRepository;
    private WebClient webClient;

    @Autowired
    public DistanceApiService(CityStorageRepository cityStorageRepository,
                              DistanceApiResponseRepository distanceApiResponseRepository) {
        this.cityStorageRepository = cityStorageRepository;
        this.distanceApiResponseRepository = distanceApiResponseRepository;
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("https://api.distancematrix.ai/maps/api/distancematrix").build();

    }

    public Mono<Map<String, Integer>> getDistanceToStorage(String deliveryCity) {
        Map<String, Integer> distanceToStorage = new HashMap<>();
        cityStorageRepository.findAll().collectList().flatMap((listCityStorage) -> {
   return   webClient.get()
             .uri(uriBuilder -> uriBuilder.path("/json")
                     .queryParam("origins",deliveryCity)
                     .queryParam("destinations",listCityStorage)
                     .queryParam("key","lyot1LYlPcHMHTkqWYXtTH11cxCG3RXedJleUQQbQgjuktSJSmSWPaHDK6jwezOU").build())
             .retrieve()
             .bodyToFlux(DistanceApiResponse.class)
                     .flatMap(distanceApiResponse -> {
                        return distanceApiResponseRepository.save(distanceApiResponse);
                     })
             .flatMap(distanceApiResponse -> {
                  distanceToStorage.put(distanceApiResponse.getCityStorage(),distanceApiResponse.getDistance());
             });
//
//
// сохрани кеш и сформируй мапу для ретурна


        });
        return Mono.just(distanceToStorage);
    };
}
