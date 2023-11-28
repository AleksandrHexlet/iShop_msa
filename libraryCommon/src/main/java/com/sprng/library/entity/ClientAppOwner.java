package com.sprng.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ClientAppOwner extends LoginData {
   // status,  List<ClientApp>список зарегистрированнных на него приложений, сумма у него на счету

    @OneToMany
    private List<ClientRegisterData> appList = new ArrayList<>();

    public List<ClientRegisterData> getAppList() {
        return appList;
    }

    public void setAppList(List<ClientRegisterData> appList) {
        this.appList = appList;
    }
}
