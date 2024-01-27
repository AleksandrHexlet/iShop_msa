package com.sprng.staff.stock.repository;

import com.sprng.staff.stock.model.StaffStock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StaffStockRepository extends ReactiveMongoRepository<StaffStock, Integer> {
    Mono<StaffStock> findByNamePosition(String namePosition);
}
