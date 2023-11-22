package com.sprng.users.repository;


import com.sprng.library.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer>,LoginDataRepository<Customer> {

}
