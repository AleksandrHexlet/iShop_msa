package com.sprng.users.repository;

import com.sprng.library.entity.ProductTrader;
import org.springframework.data.repository.CrudRepository;

public interface ProductTraderRepository extends CrudRepository<ProductTrader, Integer>,LoginDataRepository<ProductTrader> {
    ProductTrader findByName(String productTraderName);

}
