package com.sprng.feedback;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan({"controller","model"})

@EntityScan({"com.sprng.library.entity"})
@SpringBootApplication
public class FeedBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedBackApplication.class,args);
    }
}
