package com.sprng.users.repository;

import com.sprng.library.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepositoryAdmin  extends CrudRepository<Product, Integer> {
}
