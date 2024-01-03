package com.sprng.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

public class CategoryApplication {
   static Logger log = LoggerFactory.getLogger(CategoryApplication.class);
    public static void main(String[] args) {
        log.info("Hello main");

        String result= "My result == ";
        String result1= "My result № 1 == ";
        log.trace("{} Hello {} Trace",result, result1); // -> log.trace("result Hello  result1 Trace",result, result1);
        log.info("Hello Info");
        log.debug("Hello DEBUG");
        log.error("Hello Error");
        SpringApplication.run(CategoryApplication.class,args);
    }
}
