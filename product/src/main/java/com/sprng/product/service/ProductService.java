package com.sprng.product.service;

import com.sprng.library.entity.ProductTrader;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.product.module.Product;
import com.sprng.product.module.ProductFullInfo;
import com.sprng.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service

public class ProductService {
    private ProductRepository productRepository;
    private ClientCategoryService clientCategoryService;
    private ClientUserService clientUserService;

    @Autowired
    public ProductService(ProductRepository productRepository, ClientCategoryService clientCategoryService, ClientUserService clientUserService) {
        this.productRepository = productRepository;
        this.clientCategoryService = clientCategoryService;
        this.clientUserService = clientUserService;
    }

    public Mono<Product> createProduct(Product product, JwtAuthenticationToken authenticationToken) {
        String tokenValue = authenticationToken.getToken().getTokenValue();
        String userNameTrader = authenticationToken.getToken().getSubject();

        Mono<Boolean> categoryResult = clientCategoryService.existsCategoryByID(product.getCategoryProductID(), tokenValue);
        Mono<Boolean> traderResult = clientUserService.existsByIdAndUsernameAndRoleType(product.getProductTraderID(),
                userNameTrader, "trader", tokenValue);
        return Mono.zip(categoryResult, traderResult)
                .flatMap(tuple -> {
                    if (tuple.getT1() && tuple.getT2()) {
                        return productRepository.save(product);
                    }
                    return Mono.error(new IshopResponseException("category or trader not found"));
                });
    }

public Mono<ProductFullInfo> getProductFullInfo(String[] id){
        Mono<ProductTrader> traderList = clientUserService.getAllTraderById(id);

}

    public Mono<Page<Product>> getProductsByCategoryID(int id, Pageable pageable) {
        return productRepository.findAllByCategoryProductID(id, pageable)
                .collectList()
                .zipWith(productRepository.count())
                .map(tuple -> new PageImpl<Product>(tuple.getT1(), pageable, tuple.getT2()));
    }
}


