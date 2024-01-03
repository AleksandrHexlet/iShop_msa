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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    //public Mono<ProductFullInfo> getProductFullInfo(String[] TraderId){
    public Mono<ProductFullInfo> getProductFullInfo(String TraderId) {
        Mono<List<ProductTrader>> traderList = clientUserService.getAllTraderById(TraderId);
        return null;
    }

    public Flux<Page<ProductFullInfo>> getProductsByCategoryID(int id, Pageable pageable) {
        return productRepository.findAllByCategoryProductID(id, pageable) // back List<Product>
                .flatMap((products) -> {
                    String ids = products.stream().map(product -> String.valueOf(product
                                    .getProductTraderID()))
                            .collect(Collectors.joining(","));
                    return Flux.just(products).zipWith(clientUserService.getAllTraderById(ids));
                })
                .flatMap(tuple -> {
                    List<Product> productList = tuple.getT1();
                    Map<Integer, ProductTrader> productTraderList = tuple.getT2().stream().collect(Collectors.toMap(
                            productTrader -> productTrader.getId(),
                            productTrader -> productTrader)
                    );
                    List<ProductFullInfo> productFullInfoList = new ArrayList<>();
//                    productFullInfoList.
                    for (int i = 0; i < productList.size(); i++) {
                        // перебор продуктлиста и достаем оттуда продукттрайдерИД и находим этот ИД в мапе
                        // и добавляем трейдера в продуктфуллинфо
                        if (productList.get(i).getProductTraderID() == productTraderList.get(i).getId()) {
                            productFullInfoList.get(i).setTraderName(productTraderList.get(i).getName());
                            productFullInfoList.get(i).setTraderQualityIndex(productTraderList.get(i).getTraderQualityIndex());

                            productFullInfoList.get(i).setNameProduct(productList.get(i).getNameProduct());
                            productFullInfoList.get(i).setUrlImage(productList.get(i).getUrlImage());
                            productFullInfoList.get(i).setPrice(productList.get(i).getPrice());
                            productFullInfoList.get(i).setRating(productList.get(i).getRating());
                            productFullInfoList.get(i).setQuantityStock(productList.get(i).getQuantityStock());
                            productFullInfoList.get(i).setDateAdded(productList.get(i).getDateAdded());
                            productFullInfoList.get(i).setProductTraderID(productList.get(i).getProductTraderID());
                            productFullInfoList.get(i).setCategoryProductID(productList.get(i).getCategoryProductID());

                        }
                    }
                    return Flux.just(productFullInfoList);
                })
                .zipWith(productRepository.count())
                .map(tuple -> new PageImpl<ProductFullInfo>(tuple.getT1(), pageable, tuple.getT2()));
    }
}


//                .flatMap((products) -> products.stream())
//                        .zipWith(clientUserService.getAllTraderById(""))//все id положи в одну строку и положи getAllTraderById
//                .collectList()
//
//\product\src\main\java\com\sprng\product\service\ProductService.java
//
////public Mono<ProductFullInfo> getProductFullInfo(String[] TraderId){
//public Mono<ProductFullInfo> getProductFullInfo(String[] TraderId){
//        Mono<ProductTrader> traderList = clientUserService.getAllTraderById(TraderId);
//        return null;
//        }
//public Mono<Page<Product>> getProductsByCategoryID(int id, Pageable pageable) {
//        return productRepository.findAllByCategoryProductID(id, pageable) // back List<Product>
//        .flatMap((products) ->{
//        String ids = products.stream().map(product -> product.getProductTraderID.toString()).collect(Collectors.joining(","));
//        return Mono.just(products).zipWith(clientUserService.getAllTraderById(ids))
//        } )
//        .flatMap(tuple -> {
//        List<Product> productList = tuple.getT1();
//        List<ProductTrader> productTraderList = tuple.getT2();
//        List<ProductFullInfo> productFullInfoList = new ArrayList<>()
////                    productFullInfoList.
//        for (int i = 0; i < productList.size; i++) {
//        if(productList[i].getId == productTraderList[i].getId()){
//        productFullInfoList[i].setTraderName(productTraderList[i].getName());
//        productFullInfoList[i].setTraderQualityIndex(productTraderList[i].getTraderQualityIndex());
//
//        productFullInfoList[i].setNameProduct(productList[i].getNameProduct());
//        productFullInfoList[i].setUrlImage(productList[i].getUrlImage());
//        productFullInfoList[i].setPrice(productList[i].getPrice());
//        productFullInfoList[i].setRating(productList[i].getRating());
//        productFullInfoList[i].setQuantityStock(productList[i].getQuantityStock());
//        productFullInfoList[i].setDateAdded(productList[i].getDateAdded());
//        productFullInfoList[i].setProductTraderID(productList[i].getProductTraderID());
//        productFullInfoList[i].setCategoryProductID(productList[i].getCategoryProductID());
//        }
//        }
//// допиши
//        return Mono.just(productFullInfoList)
//        })
//        .zipWith(clientUserService.getAllTraderById()) //все id положи в одну строку и положи getAllTraderById
////                .collectList()
//        .zipWith(productRepository.count())
//        .map(tuple -> new PageImpl<ProductFullInfo>(tuple.getT1(), pageable, tuple.getT2()));
//        }