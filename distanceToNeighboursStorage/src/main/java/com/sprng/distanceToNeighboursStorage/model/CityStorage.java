package com.sprng.distanceToNeighboursStorage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;

@Document
public class CityStorage {
    @Id
    private String cityStorage;
    private String address;
    private int square;
    private String storageMode;

    public CityStorage(String cityStorage) {
        this.cityStorage = cityStorage;
    }

    public CityStorage() {
    }

    public String getCityStorage() {
        return cityStorage;
    }

    public void setCityStorage(String cityStorage) {
        this.cityStorage = cityStorage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public String getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }
}
