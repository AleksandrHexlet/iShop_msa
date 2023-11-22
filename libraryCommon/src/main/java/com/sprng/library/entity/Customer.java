package com.sprng.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "Customer")
@PrimaryKeyJoinColumn(name = "customerId") // от стратегии JOINED
// @DiscriminatorValue("12345") //от стратегии   SINGLE_TABLE
public class Customer extends LoginData {
//    @Id  // убрали id из-за стратегии наследования JOINED в LOGINDATA
//    @GeneratedValue
//    private int id;
    @OneToMany(mappedBy="customer")
    private List<FeedBack> feedBack = new ArrayList<>();

    @Size(min = 2,max = 99)
    private String name;
    @Size(min = 2,max = 29)
    private String city;


    @OneToMany(mappedBy="customer")
    private List<CustomerOrder> order = new ArrayList<>();


    public Customer() {
    }

    public Customer( List<FeedBack> feedBack, String userName, String userPassword, String name, String city) {
//        setId(id);
        this.feedBack = feedBack;

        this.name = name;
        this.city = city;
    }

//    public int getId() {
//        return id;
//    }

    public List<FeedBack> getFeedBack() {
        return feedBack;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public void setFeedBack(List<FeedBack> feedBack) {
        this.feedBack = feedBack;
    }




    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setOrder(List<CustomerOrder> order) {
        this.order = order;
    }
}
