package com.sprng.orderService.model;

import org.springframework.data.annotation.Id;

public class OrderDataDTO {

    private  int idProduct;
    private int countProduct;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }
}
