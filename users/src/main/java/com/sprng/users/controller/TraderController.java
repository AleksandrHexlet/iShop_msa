package com.sprng.users.controller;

import com.sprng.library.entity.Customer;
import com.sprng.library.entity.ProductTrader;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.ProductTraderRepository;
import com.sprng.users.service.ProductTraderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/users/trader")
public class TraderController {
    ProductTraderService productTraderService;

    @Autowired
    public TraderController(ProductTraderService productTraderService) {
        this.productTraderService = productTraderService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> traderRegistration(@Valid @RequestBody ProductTrader productTrader) {

        try {
            System.out.println("productTraderFr ВАСЯ!!!!");
            ProductTrader productTraderFromDB = productTraderService.traderRegistration(productTrader);
            System.out.println("productTraderFromDB.id === " + productTraderFromDB.getId());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}").buildAndExpand(productTraderFromDB.getId()).toUri();
            System.out.println("users.ProductTrader location == " + location.toString());
            return ResponseEntity.created(location).build();
//                    <ProductTrader>(productTraderFromDB, HttpStatus.CREATED);
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
            // вернем id; name, quolityIndex наприши свой sql запрос в репо и создай новую энтити шортпродуктТрейдер
            return traderList;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Traders not found");
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/down/rating")
    public ResponseEntity<String> downTraderRating(int traderId, double traderRating){
      try{
          productTraderService.downRatiting(traderId, traderRating);
          return new ResponseEntity<>("Success", HttpStatusCode.valueOf(200));
      } catch (IshopResponseException exception){
          throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
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