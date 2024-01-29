package com.sprng.order.handler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StafStockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StafStockServiceApplication.class,args);
    }

}
