package com.sprng.orderService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;

@Document
public class CustomerOrder {
    @Id
    private int id;
    private List<OrderDataDTO> orderDataDTO;
    private StatusOrder statusOrder;
    private String userNameCustomer;
    private LocalDateTime createOrder;

    private int DiscountOrder;
//    private double


    public enum StatusOrder {
        NEW,
        PAID,
        NOT_PAID,
        CLOSE_ORDER,
        CANCEL_ORDER,
        RETURN_PRODUCT,
        DELIVERED,
        SENT_FOR_DELIVERY;
        private LocalDateTime statusAssigned;

        public void setStatusAssigned(LocalDateTime statusAssigned) {
            this.statusAssigned = statusAssigned;
        }

        public LocalDateTime getStatusAssigned() {
            return statusAssigned;
        }
    }


    public List<OrderDataDTO> getOrderDataDTO() {
        return orderDataDTO;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public String getUserNameCustomer() {
        return userNameCustomer;
    }

    public LocalDateTime getCreateOrder() {
        return createOrder;
    }

    public int getId() {
        return id;
    }
    //    com.sprng.library.entity.CustomerOrder.OrderStatus.NEW.setStatusAssigned(LocalDateTime.now())

}
