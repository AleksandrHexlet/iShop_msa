package com.sprng.users.repository;

import com.sprng.library.entity.ProductTrader;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductTraderRepository extends CrudRepository<ProductTrader, Integer>,LoginDataRepository<ProductTrader> {
    ProductTrader findByName(String productTraderName);

    List<ProductTrader> findAllTraderById(String[] id);

}
