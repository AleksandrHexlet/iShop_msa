package com.sprng.library.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sprng.library.deserializer.CustomerOrderDeserialized;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonDeserialize(using = CustomerOrderDeserialized.class)
public class CustomerOrder {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "customerOrder")
    private List<ProductCustomerOrder> productCustomerOrderList = new ArrayList<>();
    //    select FROM customerOrder LEFT JOIN ProductCustomerOrder
//    on ProductCustomerOrder.customerOrder = customerOrder(this)
    private LocalDate date;
    private LocalDate dateUpdateStatus;
    private OrderStatus status;
    private String deliveryAddress;

    public enum OrderStatus {
        ORDER_ACCEPTED,
        ORDER_FORMED,
        ORDER_PAID,
        ORDER_SENT_BUYER,
        ORDER_DELIVERED,
        ORDER_CANCELLED,


    }

    public CustomerOrder() {
    }

    public CustomerOrder(Customer customer, LocalDate date, LocalDate dateUpdateStatus, OrderStatus status, String deliveryAddress) {
        this.customer = customer;
        this.date = date;
        this.dateUpdateStatus = dateUpdateStatus;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
    }

    public void addListProductCustomerOrder(ProductCustomerOrder productCustomerOrder) {
        productCustomerOrder.setCustomerOrder(this);
        productCustomerOrderList.add(productCustomerOrder);
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<ProductCustomerOrder> getProductCustomerOrderList() {
        return productCustomerOrderList;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getDateUpdateStatus() {
        return dateUpdateStatus;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProductCustomerOrder(List<ProductCustomerOrder> productCustomerOrder) {
        this.productCustomerOrderList = productCustomerOrder;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDateUpdateStatus(LocalDate dateUpdateStatus) {
        this.dateUpdateStatus = dateUpdateStatus;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
