package com.sprng.library.entity;


import jakarta.persistence.Entity;

import java.util.Set;

@Entity

public class ClientApp extends LoginData {

    private String clientSecretClean;
    private String clientName;
    private String password;
    private String redirectURL;
    //    private Set<String> redirectURL;
    private String postLogoutRedirectURL;
    private Set<String> scopes;

    public String getClientSecretClean() {
        return clientSecretClean;
    }

    public ClientApp(String userName, String password, String clientSecret) {
        super(userName, password);
        this.clientSecretClean = clientSecret;
    }

    public ClientApp() {

    }

    public void setClientSecretClean(String clientSecret) {
        this.clientSecretClean = clientSecret;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getPostLogoutRedirectURL() {
        return postLogoutRedirectURL;
    }

    public void setPostLogoutRedirectURL(String postLogoutRedirectURL) {
        this.postLogoutRedirectURL = postLogoutRedirectURL;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }
}

// от клиента приходи бюджет( ) и названия товаров сервер возращает несколько
// списков(2-3) какие товары он может купить на этот бюджет.
// Пришла 1 тыс рублей и название товаров(лыжи, ботинки и шлем) Например: лыжи за
// 500 р + ботинки за 300 + шлем за 200 рублей или только  лыжи за
// 500 р + ботинки за 500. Если нет ни одного товара значит пишет, что
// бюджет маленький и нет подходящих товаров. Либо только что-то одно может купить например
// только лыжи за 1 тысячу
// Список выглядит только по одному продавцу на каждую позицию и только по одному экземпляру товара.
// Двое лыж нельзя. Если у 2 продавцов одинаковый товар и одинаковая стоимость,
// тогда предлагай того у кого рейтинг выше.Если рейтинг одинаковый тогда у того у кого тариф.
// Если тариф одинаковый тогда рандомно определяй кого
// Каждый из вариантов покупок это лист с возможными товарами.
// Реализация создай продукты в БД (товары с одинаковыми названиями от разных продавцов),
// потом написать метод в репозитории(findByPriceLessThanAndNameProductIn) поиска товара и стоимостью
// ниже чем указано в методе.
// List<Product> findByPriceLessThanAndNameProductIn(BigDecimal price, Collection<String> names);
// Отсортировать лист по производителю и потом внутри одного производителя брать каждый товар по одному и
// самые дешевые. Если самые дешевые не укладываются в бюджет тогда один товар выкинули. Если все уложились и бюджет остался, тогда смотри товары
// более дорогие товары. Берём один товар и смотрим его с более дорогой стоимостью, чтобы уложиться.
// Если перебор по цене, тогда ищем этот товар но более дешевый. Если наша покупка меньше бюджета
// менее чем на 10 процентов, тогда это ОК и мы возвращаем список товаров.
// В каждом списке могут быть товары и одного производителя и разных производителей