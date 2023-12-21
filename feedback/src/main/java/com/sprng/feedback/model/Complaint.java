package com.sprng.feedback.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;

@Document(collection = "Complaint")
public class Complaint {
    @Id
    private int id;
    @NotNull
    @Size(min = 3, max = 280)
    private String textComplaint;
    @Positive
    private int productTraderId;
    @Positive
    private int customerId;

    private  boolean statusComplaint;
    private ZonedDateTime createdAt;

    public String getTextComplaint() {
        return textComplaint;
    }

    public void setTextComplaint(String textComplaint) {
        this.textComplaint = textComplaint;
    }

    public int getProductTraderId() {
        return productTraderId;
    }

    public void setProductTraderId(int productTraderId) {
        this.productTraderId = productTraderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isStatusComplaint() {
        return statusComplaint;
    }

    public void setStatusComplaint(boolean statusComplaint) {
        this.statusComplaint = statusComplaint;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
