package com.sprng.staff.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StaffStockEvent {
    @Id
    int id = (int)Math.random()*100000;

    String initiator;
    @JsonProperty(value = "namePosition")
    private String namePosition;

    @JsonProperty(value = "count")
    private int count;

    public StaffStockEvent(String namePosition, int count) {
        this.namePosition = namePosition;
        this.count = count;
    }

    public StaffStockEvent() {
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
};
