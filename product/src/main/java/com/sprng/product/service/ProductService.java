package com.sprng.product.service;

import com.sprng.library.exception.IshopResponseException;
import com.sprng.product.module.Product;
import com.sprng.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public class ProductService {
    ProductRepository productRepository;
    ClientCategoryService clientCategoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, ClientCategoryService clientCategoryService) {
        this.productRepository = productRepository;
        this.clientCategoryService = clientCategoryService;
    }

    public Mono<Product> createProduct(Product product) {
        return clientCategoryService.existsCategoryByID(product.getCategoryProductID())
                .flatMap((booleanValue) -> {
                    if (booleanValue) {
                        return productRepository.save(product);
                    }
                    return Mono.error(new IshopResponseException("category not found"));
                });
    }


}


