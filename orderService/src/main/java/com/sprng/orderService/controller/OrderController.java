package com.sprng.orderService.controller;

import com.sprng.orderService.model.OrderDataDTO;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @PostMapping
    public void createOrder(@RequestBody List<OrderDataDTO> orderDataList, JwtAuthenticationToken token)
    {

    }

}
