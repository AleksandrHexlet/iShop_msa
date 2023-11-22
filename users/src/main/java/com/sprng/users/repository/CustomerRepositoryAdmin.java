package com.sprng.users.repository;

import com.sprng.library.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepositoryAdmin extends CrudRepository<Customer, Integer> {

//    Customer findByName(String usernameOut); Я могу getCustomersByUserName заменить
//                                      на findByName? у нас в Customer есть и name и username

//JPQL not work
//    @Query("SELECT customer FROM Customer customer WHERE customer.userName = :usernameOUT")
//        public Customer getCustomersByUserName(String usernameOut);


    @Query(nativeQuery = true, value = "SELECT * FROM customer WHERE userName = :usernameOUT")
    public Optional<Customer> getCustomersByUserName(String usernameOUT);
}
