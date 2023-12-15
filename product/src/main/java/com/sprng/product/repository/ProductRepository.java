package com.sprng.product.repository;

import com.sprng.product.module.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ProductRepository extends ReactiveMongoRepository<Product,Integer> {

    Flux<List<Product>> findAllByCategoryProductID(int categoryProductID, Pageable pageable);
}
