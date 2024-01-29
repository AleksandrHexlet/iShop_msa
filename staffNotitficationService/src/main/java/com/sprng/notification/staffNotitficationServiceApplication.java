package com.sprng.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class staffNotitficationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(staffNotitficationServiceApplication.class,args);
    }

}
