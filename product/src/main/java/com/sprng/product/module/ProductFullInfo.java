package com.sprng.product.module;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductFullInfo extends Product {

    private String traderName;
    private double traderQualityIndex;

    public ProductFullInfo() {
    }

    public ProductFullInfo(String nameProduct, String urlImage, BigDecimal price,
                           double rating, int quantityStock, LocalDate dateAdded,
                           int productTraderID, int categoryProductID, String traderName,
                           double traderQualityIndex) {
        super(nameProduct, urlImage, price, rating, quantityStock, dateAdded, productTraderID,
                categoryProductID);
        this.traderName = traderName;
        this.traderQualityIndex = traderQualityIndex;
    }

}
