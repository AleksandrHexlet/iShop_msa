package com.sprng.feedback.integration;

import com.sprng.feedback.model.FeedBack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureDataMongo
//@WebFluxTest
public class ControllerServiceRepoTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void controllerTest(){
        webTestClient.get().uri("/api/feedback/111")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedBack.class)
                .value(feedBack -> {
                    assertEquals(feedBack.getId(),111);
                    assertNotNull(feedBack);
//                    assertDoesNotThrow(feedBack-> )
                    assertDoesNotThrow(()-> new IOException(),"Не удалось получить отзывы");
                });
        System.out.println("Vasya");

    }


    @Test
    public void postRequestTest(){
        FeedBack feedbackObj = new FeedBack();
        feedbackObj.setText("QQQ111");
        feedbackObj.setProductID(111);
        webTestClient.post().uri("/api/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(feedbackObj), FeedBack.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FeedBack.class)
                .value(feedBack -> {
                    assertNotNull(feedBack);
                    assertEquals(feedBack.getProductID(),feedbackObj.getProductID());
                    assertEquals(feedBack.getText(),feedbackObj.getText());
                    assertDoesNotThrow(()->new IOException(), "нет такого отзыва");
                });
    }

}
