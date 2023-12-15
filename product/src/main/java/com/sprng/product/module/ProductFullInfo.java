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

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public double getTraderQualityIndex() {
        return traderQualityIndex;
    }

    public void setTraderQualityIndex(double traderQualityIndex) {
        this.traderQualityIndex = traderQualityIndex;
    }
}
