package com.sprng.library.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
public class AdminIshop  extends LoginData{
    private String name;
    private ZonedDateTime dateLastActivity;
    public AdminIshop() {
    }

    public AdminIshop(String name) {
        this.name = name;
    }

    public void setDate(ZonedDateTime date) {
        this.dateLastActivity = dateLastActivity;
    }
}
