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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

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
    public ResponseEntity<ProductTrader> traderRegistration(@Valid @RequestBody ProductTrader productTrader) {
        System.out.println("productTrader === " + productTrader);
        try {
            ProductTrader productTraderFromDB = productTraderService.traderRegistration(productTrader);
            return new ResponseEntity<ProductTrader>(productTraderFromDB, HttpStatus.CREATED);
        } catch (IshopResponseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверные данные для регистрации продавца");
        }
    }

    @GetMapping("/get/traders")
    public List<ProductTrader> getTraders(@RequestParam String id) {
        try {
           List<Integer> ids =  Arrays.stream(id.split(","))
                   .map(str -> Integer.parseInt(str)).toList();

            List<ProductTrader> traderList = productTraderService.getAllTraderById(ids);
            return traderList;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Traders not found");
        }
    }

}


//    Сервис Продукты
//1. [POST] Добавить продукт.
//        Нельзя добавить продукт в несуществующую категорию.
//        2. [POST] Добавить несколько продуктов из файла.
//        Нельзя добавить продукт в несуществующую категорию.
//        3. [GET] Продукты по id категории (пагинация, плюс продавец каждого)
//        4. [GET] Продукты по id продавца (пагинация, плюс категория каждого)
//        5. [GET] Продукты по названию (плюс категория каждого, плюс продавец каждого)
//        6. [GET] Продукты по нескольким характеристикам (плюс категория каждого, плюс продавец каждого)
//        7. [GET] Продукты по названиям на заданную сумму (плюс категория каждого, плюс продавец каждого)
//        8. [GET] Продукт по id (плюс отзывы??, плюс категория, плюс продавец)
//        ??? Какие характеристики продукта, категории, продавца будут передаваться клиенту для пунктов №3 - №7 ???