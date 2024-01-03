package com.sprng.distanceToNeighboursStorage.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;


@JsonDeserialize(using = ResponseDeserialized.class)
public class DistanceApiResponse {
    @Id
    int id;
    @JsonProperty("origin") // куда -- покупатель
    private String cityDelivery;
    @JsonProperty("destination") // откуда --- склад
    private String cityStorage;
    private int distance;

    public String getCityDelivery() {
        return cityDelivery;
    }

    public void setCityDelivery(String cityDelivery) {
        this.cityDelivery = cityDelivery;
    }

    public String getCityStorage() {
        return cityStorage;
    }

    public void setCityStorage(String cityStorage) {
        this.cityStorage = cityStorage;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}


