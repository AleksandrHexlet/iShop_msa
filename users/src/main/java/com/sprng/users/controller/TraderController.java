package com.sprng.users.controller;

import com.sprng.library.entity.Customer;
import com.sprng.library.entity.ProductTrader;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.ProductTraderRepository;
import com.sprng.users.service.ProductTraderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users/trader")
public class TraderController {
    ProductTraderService productTraderService;

    @Autowired
    public TraderController(ProductTraderService productTraderService) {
        this.productTraderService = productTraderService;
    }

//    @PostMapping
//    public ResponseEntity<ProductTrader> registrationTrader(String login, String password){
//        try{
//            ProductTrader productTrader = productTraderService.getProductTrader(username);
//            return new ResponseEntity<Customer>(productTrader, HttpStatus.FOUND);
//        }catch (Exception exception){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProductTrader not found");
//        }
//    }

    @PostMapping("/registration")
    public ResponseEntity<ProductTrader> traderRegistration(@Valid @RequestBody ProductTrader productTrader){
        System.out.println("productTrader === " + productTrader);
        try{
            ProductTrader productTraderFromDB = productTraderService.traderRegistration(productTrader);
            return new ResponseEntity<ProductTrader>(productTraderFromDB, HttpStatus.FOUND);
        } catch(IshopResponseException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверные данные для регистрации продавца");

        }

    }


}
