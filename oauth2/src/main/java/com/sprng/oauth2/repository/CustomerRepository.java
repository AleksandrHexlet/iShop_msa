package com.sprng.oauth2.repository;

import com.sprng.library.entity.Customer;
import com.sprng.users.repository.LoginDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer>, LoginDataRepository<Customer> {

}
