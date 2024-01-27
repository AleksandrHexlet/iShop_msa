package com.sprng.order.handler.events;

import org.springframework.data.mongodb.core.mapping.Document;



public class OrderEvent {
    private String namePosition;
    private int count;
    private String employeesNumber;

    public OrderEvent() {
    }

    public OrderEvent(String namePosition, int count, String employeesNumber) {
        this.namePosition = namePosition;
        this.count = count;
        this.employeesNumber = employeesNumber;
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
