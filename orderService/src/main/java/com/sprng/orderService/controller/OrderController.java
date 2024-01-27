package com.sprng.orderService.controller;

import com.sprng.orderService.model.CustomerOrder;
import com.sprng.orderService.model.OrderDataDTO;
import com.sprng.orderService.model.PaymentData;
import com.sprng.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }



    @PostMapping
    public Mono<CustomerOrder> createOrder(@RequestBody List<OrderDataDTO> orderDataList, JwtAuthenticationToken token) {
        return null;
    }

    @PostMapping("/pay")
    public void payOrder(@RequestBody PaymentData paymentData) {
        orderService.payOrder(paymentData);
    }


}
