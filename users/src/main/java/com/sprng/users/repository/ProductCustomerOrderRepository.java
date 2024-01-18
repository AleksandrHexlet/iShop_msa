package com.sprng.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCustomerOrderRepository  extends JpaRepository<ProductCustomerOrder, Integer> {
List<ProductCustomerOrder> findByProductTraderUserName(String userName);
boolean existsByProductIdAndProductTraderUserName(int productId, String userName);

}
