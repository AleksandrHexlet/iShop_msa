package com.sprng.notification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StaffStock {
    @Id
    int id = (int)(Math.random()*100000);
    @JsonProperty(value = "namePosition")
    private String namePosition;

    @JsonProperty(value = "count")
    private int count;

    @JsonProperty(value = "neededOrder")
    private int neededOrder;

    private boolean isSuccess;



    public StaffStock(String namePosition, int count) {
        this.namePosition = namePosition;
        this.count = count;
    }

    public StaffStock() {
    }

    public int getId() {
        return id;
    }

    public String getNamePosition() {
        return namePosition;
    }

    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNeededOrder() {
        return neededOrder;
    }

    public void setNeededOrder(int neededOrder) {
        this.neededOrder = neededOrder;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
};
