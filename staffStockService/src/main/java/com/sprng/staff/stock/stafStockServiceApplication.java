package com.sprng.staff.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class stafStockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(stafStockServiceApplication.class,args);
    }

}
