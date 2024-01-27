package com.sprng.order.handler.repository;


import com.sprng.order.handler.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderHandlerRepository extends ReactiveMongoRepository<Order, Integer> {
}
