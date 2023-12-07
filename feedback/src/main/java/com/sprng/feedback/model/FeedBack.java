package com.sprng.feedback.model;

import java.time.ZonedDateTime;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public class FeedBack {

    @Id
    private int id;
    @Positive
    private int customerID;
    private ZonedDateTime timeAdded;

    @Size(min = 3, max = 500)
    private String text;

    @Positive
    private int productID;

    private boolean isDelete;

    public FeedBack() {
    }

    public FeedBack(String text, int product, int customer) {

        this.timeAdded = ZonedDateTime.now();
        this.text = text;
        this.productID = product;
        this.customerID = customer;

    }

    public int getId() {
        return id;
    }

    public ZonedDateTime getTimeAdded() {
        return timeAdded;
    }

    public String getText() {
        return text;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setTimeAdded(ZonedDateTime timeAdded) {
        this.timeAdded = timeAdded;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void delete() {
        isDelete = true;
    }

}
