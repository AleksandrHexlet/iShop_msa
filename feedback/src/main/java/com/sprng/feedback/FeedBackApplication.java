package com.sprng.feedback;


@EntityScan({"com.sprng.library.entity"})
@SpringBootApplication
public class FeedBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedBackApplication.class,args);
    }
}
