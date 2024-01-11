package com.sprng.users.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductFullInfo extends Product {
    private String traderName;
    private double traderQualityIndex;
    private double costDelivery;
    private int distanceToStorage;
    public ProductFullInfo() {
    }

    public ProductFullInfo(String traderName, double traderQualityIndex, double costDelivery, int distanceToStorage) {
        this.traderName = traderName;
        this.traderQualityIndex = traderQualityIndex;
        this.costDelivery = costDelivery;
        this.distanceToStorage = distanceToStorage;
    }

    public ProductFullInfo(String nameProduct, String urlImage, String cityStorage, BigDecimal price, double rating, int quantityStock, LocalDate dateAdded, int productTraderID, int categoryProductID, String traderName, double traderQualityIndex, double costDelivery, int distanceToStorage) {
        super(nameProduct, urlImage, cityStorage, price, rating, quantityStock, dateAdded, productTraderID, categoryProductID);
        this.traderName = traderName;
        this.traderQualityIndex = traderQualityIndex;
        this.costDelivery = costDelivery;
        this.distanceToStorage = distanceToStorage;
    }

    public double getCostDelivery() {
        return costDelivery;
    }

    public void setCostDelivery(double costDelivery) {
        this.costDelivery = costDelivery;
    }

    public int getDistanceToStorage() {
        return distanceToStorage;
    }

    public void setDistanceToStorage(int distanceToStorage) {
        this.distanceToStorage = distanceToStorage;
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
