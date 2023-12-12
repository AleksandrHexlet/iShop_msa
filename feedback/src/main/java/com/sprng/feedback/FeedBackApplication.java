package com.sprng.feedback;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan({"com.sprng.library.entity"})
@SpringBootApplication
public class FeedBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedBackApplication.class,args);
    }
}
