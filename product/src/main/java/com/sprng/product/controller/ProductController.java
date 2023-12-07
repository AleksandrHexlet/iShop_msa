package com.sprng.product.controller;


import com.sprng.product.module.Product;
import com.sprng.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/product")

public class ProductController {
   private ProductService productService;

   @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody @Valid Product product){
        return productService.createProduct(product).doOnError((throwable)-> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,throwable.getMessage());
        });
    }


}

//
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