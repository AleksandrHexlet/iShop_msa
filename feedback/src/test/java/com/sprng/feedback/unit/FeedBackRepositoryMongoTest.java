package com.sprng.feedback.unit;

import com.sprng.feedback.model.FeedBack;
import com.sprng.feedback.repository.FeedBackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class FeedBackRepositoryMongoTest {
    @Autowired
    ReactiveMongoOperations operations;

    @Autowired
    FeedBackRepository feedBackRepository;

    @BeforeEach
    public void createCollection(){
        operations.createCollection(FeedBack.class).then().block();
    }

    @Test
    public void feedBackRepositoryTest() {
        FeedBack feedBack = new FeedBack();
        feedBack.setText("QQQ");
        feedBack.setProductID(111);
        FeedBack feedBack1 = new FeedBack();
        feedBack.setText("WWW");
        feedBack1.setProductID(222);
        FeedBack feedBack2 = new FeedBack();
        feedBack.setText("EEE");
        feedBack2.setProductID(333);

        feedBackRepository.save(feedBack);
        feedBackRepository.save(feedBack1);
        feedBackRepository.save(feedBack2);
        List<FeedBack> feedBackList = new ArrayList<>();
        feedBackList.add(feedBack);
        feedBackList.add(feedBack1);
        feedBackList.add(feedBack2);

        assertThat(feedBackList).contains(feedBack);
        assertThat(feedBackList).isNotNull();

        feedBackRepository.findAllByProductIDAndIsDeleteFalse(111)
                .toIterable().forEach(feedBackFromDB -> {
                    assertAll("assertGroupOur",
                            () -> assertEquals(feedBackFromDB.getId(), feedBack.getId()),
                            () -> assertEquals(feedBackFromDB.getText(), feedBack.getText())
                    );
                });
//          operations.dropCollection(FeedBack.class);
    }

}
