package com.sprng.distanceToNeighboursStorage.controller;

import com.sprng.distanceToNeighboursStorage.service.DistanceApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/delivery")
public class CalculationCostDelivery {
    private DistanceApiService distanceApiService;

    @Autowired
    public CalculationCostDelivery(DistanceApiService distanceApiService) {
        this.distanceApiService = distanceApiService;
    }

    @GetMapping("/cost")
    public Mono<Map<String, Integer>> getCostDelivery(@RequestParam String deliveryCity) {
//        ?name=Vasya&age=18& -----> RequestParam
//        name/Vasya/age/18 ---- @PathVariable
        return distanceApiService.getPriceDelivery(deliveryCity);
    }


    @GetMapping("/distance")
    public Mono<Map<String, Integer>> getDistanceDelivery(@RequestParam String deliveryCity) {
//        ?name=Vasya&age=18& -----> RequestParam
//        name/Vasya/age/18 ---- @PathVariable
        return distanceApiService.getDistanceDelivery(deliveryCity);
    }
}
