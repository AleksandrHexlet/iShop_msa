package com.sprng.order.handler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "staff_order")
public class Order {
    @Id
    private int id;
    private String namePosition;
    private int count;
    private String employeesNumber;

    public Order(int id, String namePosition, int count, String employeesNumber) {
        this.id = id;
        this.namePosition = namePosition;
        this.count = count;
        this.employeesNumber = employeesNumber;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(String employeesNumber) {
        this.employeesNumber = employeesNumber;
    }
}
