package com.sprng.product.module;


import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Document(collection = "product")
public class Product {
    @Id
    private int id;

    @Size(min=2, max = 99)
    private String nameProduct;

    @Size(min=2, max = 199)
    private String urlImage;
    @Positive
    private BigDecimal price;
    private double rating;
    //    private BigDecimal price;
//    private short rating;

    @Positive
    private int quantityStock;

    private LocalDate dateAdded;

    @Positive
    private int productTraderID;
    @Positive
    private int categoryProductID;

    public Product() {
    }

    public Product(String nameProduct, String urlImage, BigDecimal price, double rating, int quantityStock,
                   LocalDate dateAdded, int productTraderID, int categoryProductID) {

        this.nameProduct = nameProduct;
        this.urlImage = urlImage;
        this.price = price;
        this.rating = rating;
        this.quantityStock = quantityStock;
        this.dateAdded = dateAdded;
        this.productTraderID = productTraderID;

        this.categoryProductID = categoryProductID;
    }

    //Геттеры необходимы, чтобы приватные поля попали в JSON и в последствии в Базу данных.
    // Если свойства у entity класса приватные и нет геттеров, они не попадут в json.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public int getProductManufacturer() {
        return productTraderID;
    }


    public int getCategoryProductID() {
        return categoryProductID;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setProductTraderID(int productTraderID) {
        this.productTraderID = productTraderID;
    }


    public void setCategoryProductID(int categoryProductID) {
        this.categoryProductID = categoryProductID;
    }

    public int getProductTraderID() {
        return productTraderID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", categoryProductID=" + categoryProductID +
                '}';
    }
}
