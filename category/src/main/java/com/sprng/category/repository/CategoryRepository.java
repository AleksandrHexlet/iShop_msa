package com.sprng.category.repository;

import com.sprng.category.model.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;


public interface CategoryRepository extends R2dbcRepository<Category, Integer> {

    Mono<Category> findByName(String name);
}
