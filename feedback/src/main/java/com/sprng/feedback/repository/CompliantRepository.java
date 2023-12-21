package com.sprng.feedback.repository;

import com.sprng.feedback.model.Complaint;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompliantRepository extends ReactiveMongoRepository<Complaint, Integer> {

    Flux<Complaint> findAllByStatusComplaintFalseOrderByCreatedAtDesc();

    Mono<Integer> countByProductTraderId(int productTraderID);
    //    Flux<Complaint> findAllByStatusComplaintFalseAndProductTraderId(int productTraderId);
}
