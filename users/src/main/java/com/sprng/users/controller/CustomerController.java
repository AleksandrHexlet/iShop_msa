package com.sprng.users.controller;


import com.sprng.library.entity.AdminIshop;
import com.sprng.library.entity.Customer;
import com.sprng.users.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
 CustomerService customerService;

 @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> getCustomer(String username){
        try{
            Customer customer = customerService.getCustomer(username);
            return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
        }catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found");
        }
    }
}
