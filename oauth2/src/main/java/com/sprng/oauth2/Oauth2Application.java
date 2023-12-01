package com.sprng.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.sprng.library.entity")
public class Oauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class,args);
    }
}
