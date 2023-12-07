package com.sprng.product.repository;

import com.sprng.product.module.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product,Integer> {

}
