package com.sprng.library.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sprng.library.deserializer.ProductDeserialized;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@JsonDeserialize(using = ProductDeserialized.class)
public class Product {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String nameProduct;

    @Column(nullable = false)
    private String urlImage;
    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal price;
    private double rating;
    //    private BigDecimal price;
//    private short rating;
    @Column(nullable = false)
    private int quantityStock;
    @Column(nullable = false)
    private LocalDate dateAdded;


    @ManyToOne
    private ProductTrader productTrader;
    @OneToMany(mappedBy = "product")
    private List<FeedBack> feedBack = new ArrayList<>();
    @ManyToOne
    private Category categoryProduct;

//    @OneToMany(mappedBy = "parent")
//    private List<Product> children = new ArrayList<>();

    public Product() {
    }

    public Product(String nameProduct, String urlImage, BigDecimal price, double rating, int quantityStock, LocalDate dateAdded, ProductTrader productTrader, Category categoryProduct) {

        this.nameProduct = nameProduct;
        this.urlImage = urlImage;
        this.price = price;
        this.rating = rating;
        this.quantityStock = quantityStock;
        this.dateAdded = dateAdded;
        this.productTrader = productTrader;

        this.categoryProduct = categoryProduct;
    }

    public void setFeedbackToList(FeedBack feedBackOUT) {
        this.feedBack.add(feedBackOUT);
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

    public ProductTrader getProductManufacturer() {
        return productTrader;
    }

    public List<FeedBack> getFeedBack() {
        return feedBack;
    }

    public Category getCategoryProduct() {
        return categoryProduct;
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

    public void setProductManufacturer(ProductTrader productTrader) {
        this.productTrader = productTrader;
    }

    public void setFeedBack(List<FeedBack> feedBack) {
        this.feedBack = feedBack;
    }

    public void setCategoryProduct(Category categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", categoryProduct=" + categoryProduct +
                '}';
    }
}
