package com.sprng.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
//@IdClass(ProductManufactureId.class)
@PrimaryKeyJoinColumn(name = "traderId")
public class ProductTrader  extends  LoginData{
    @Size(min = 2, max = 199, message = "Длина имени категории должна быть равен или более 2 символам и менее 199 символов")
    private String name;
    private LocalDate dateRegistration;
    private double traderQualityIndex;
    @PositiveOrZero
//    @DecimalMin(value = "0")
    private BigDecimal traderBill;
    private String rate;
    private int discountFromTrader;

//    @ManyToOne
//    TraderRating traderRating;
    //    @Id
    private String cityStorage;
    private boolean isActive = true;

    private double totalSumAllRatings;
    private int totalCountAllRatings;


    public ProductTrader() {
    }

    public ProductTrader(String name, LocalDate dateRegistration,
                         BigDecimal traderBill, String rate, String cityStorage, boolean isActive
                         ) {
        this.name = name;
        this.dateRegistration = dateRegistration;
        this.traderBill = traderBill;
        this.rate = rate;
        this.cityStorage = cityStorage;
        this.isActive = isActive;

    }

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public double getTraderQualityIndex() {
        return traderQualityIndex;
    }

    public void setTraderQualityIndex(double traderQualityIndex) {
        this.traderQualityIndex = traderQualityIndex;
    }

    public double getTotalSumAllRatings() {
        return totalSumAllRatings;
    }

    public void setTotalSumAllRatings(double totalSumAllRatings) {
        this.totalSumAllRatings = totalSumAllRatings;
    }

    public int getTotalCountAllRatings() {
        return totalCountAllRatings;
    }

    public void setTotalCountAllRatings(int totalCountAllRatings) {
        this.totalCountAllRatings = totalCountAllRatings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStoreQualityIndex() {
        return traderQualityIndex;
    }

    public void setStoreQualityIndex(double storeQualityIndex) {
        this.traderQualityIndex = storeQualityIndex;
    }

    public String getCityStorage() {
        return cityStorage;
    }

    public void setCityStorage(String cityStorage) {
        this.cityStorage = cityStorage;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public BigDecimal getTraderBill() {
        return traderBill;
    }

    public void setTraderBill(BigDecimal traderBill) {
        this.traderBill = traderBill;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public boolean isActive() {
        return isActive;
    }
}

// http://127.0.0.1:9090/oauth2/authorize?response_type=code&client_id=$2a$10$dU8slMdM5sMSIDwI75sJeuwgdxr0DtfY28YhL.spiTYKLYTwNxmXK&redirect_uri=http://localhost:8888&scope=openid read