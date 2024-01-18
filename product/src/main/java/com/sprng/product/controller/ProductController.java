package com.sprng.product.controller;


import com.sprng.product.module.Product;
import com.sprng.product.module.ProductCountDTO;
import com.sprng.product.module.ProductFullInfo;
import com.sprng.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/product")

public class ProductController {
   private ProductService productService;


   @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody @Valid Product product, JwtAuthenticationToken authenticationToken){
//        JwtAuthenticationToken authenticationToken -- тут роли ,scope
        return productService.createProduct(product,authenticationToken).doOnError((throwable)-> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,throwable.getMessage());
        });
    }

    @GetMapping("products/category/{id}")
    public Flux<Page<ProductFullInfo>> getProductsByCategoryID(@PathVariable int id, @RequestParam int size,
                                                               @RequestParam int page,
                                                               JwtAuthenticationToken authenticationToken){
       return productService.getProductsByCategoryID(id,authenticationToken, PageRequest.of(page,size)).doOnError((throwable)->{
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
       });
    }

    @GetMapping("/count/{idList}")
    public Flux<ProductCountDTO> getCountProductsByID(@PathVariable List<Integer> idList){
        return productService.getCountProductsByID(idList);

    }
}
    // тут для trader вернем имя и traderQualityIndex для отображения в карточке
    // Если пользователь открывает карточку, тогда приходит отдельный запрос с id producta и мы вернем город
    // нахождения склада с товаром, отзывы о продукте и рейтинг продукта.
    // Как сделать пангинацию для webFlux and r2dbc ?



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