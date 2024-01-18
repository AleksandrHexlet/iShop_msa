package com.sprng.orderService.repository;

import com.sprng.orderService.model.CustomerOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<CustomerOrder, Integer> {

}
