package com.sprng.category.model;

import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "tb_category")
public class Category {
    @Id
    private int id;
    @Size(min = 2, max = 199, message = "Длина имени категории должна быть равен или более 2 символам и менее 199 символов")
    private String name;
    @Size(min = 2, max = 199, message = "Длина url должна быть равен или более 2 символам и менее 199 символов")
    private String url; // food sport  и т.д. это то, что отображается в строке браузера Например category/food
    @Column("parent_id")
    private Category parent;

    public Category(String name, String url, Category parent) {
        this.name = name;
        this.url = url;
        this.parent = parent;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Category{" +

                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parent=" + parent +
                '}';
    }
}
