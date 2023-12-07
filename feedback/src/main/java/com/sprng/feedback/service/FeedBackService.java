package com.sprng.feedback.service;

import com.sprng.feedback.model.FeedBack;
import com.sprng.feedback.repository.FeedBackRepository;
import com.sprng.library.exception.IshopResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FeedBackService {
    private FeedBackRepository feedBackRepository;

    @Autowired
    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    public Mono<FeedBack> createFeedBack(FeedBack feedBack) {
//        Проверяем есть ли такой customer по customerID.
//        Для этого отправляем запрос через тип WebClient на сервис users. У users должен
//        быть контроллер который нам ответить есть такой кастомер в БД или нет

//        Проверяем есть ли такой product по ProductID.
//        Для этого отправляем запрос через тип WebClient на сервис users. У users должен
//        быть контроллер который нам ответить есть такой кастомер в БД или нет
//
        return null;
    }

    public Flux<FeedBack> getFeedBacksByProductID(int productID) {
        return feedBackRepository.findAllByProductIDAndIsDeleteFalse(productID);
    }

    public Mono<FeedBack> deleteFeedBackById(int feedBackID) {
        Mono<FeedBack> feedBack = feedBackRepository.findById(feedBackID)
                .flatMap((feedback) -> {
                    feedback.delete();
                    return feedBackRepository.save(feedback);
                });
        return feedBack;
    }


}


