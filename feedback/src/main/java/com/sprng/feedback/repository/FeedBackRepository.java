package com.sprng.feedback.repository;


import com.sprng.feedback.model.FeedBack;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FeedBackRepository extends ReactiveMongoRepository<FeedBack, Integer> {
//    findByCustomerId(int Id)

    Flux<FeedBack> findAllByProductIDAndIsDeleteFalse(int productID);

}
