package com.sprng.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {


    @Query(nativeQuery = true, value = "SELECT count(id) FROM product WHERE id in(:idProductArray)")
    public int getProductsCountById(int[] idProductArray);

//    @Query("SELECT prod FROM Product prod WHERE prod.quantityStock > :quantity AND prod.price < :price ")
//    List<Product> getProductsByPriceAndQuantity(int quantity, BigDecimal price);
//
//    @Query("SELECT product FROM Product product LEFT JOIN product.productManufacturer manufacture  WHERE " +
//            "product.rating > :rating AND manufacture.country = :country")
//    List<Product> getProductsByRatingAndManufactureCountry(short rating, String country);



}
