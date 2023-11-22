package com.sprng.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="tb_categories")
//@JsonDeserialize(using = CategoryDeserialized.class)
public class Category extends IdData {

//    @Column(nullable = false,length = 199)
    @Column(nullable = false)
    @Size(min=2,max= 199, message = "Длина имени категории должна быть равен или более 2 символам и менее 199 символов")
    private String name;
    @Column(unique = true,nullable = false,columnDefinition = "TEXT NOT NULL")

    private String url; // food sport  и т.д. это то, что отображается в строке браузера Например category/food
    @ManyToOne
    private Category parent;


    public Category(String name, String url, Category parentOUT) {

        this.name = name;
        this.url = url;
        this.parent = parentOUT;
    }

    public Category() {
    }

    //Геттеры необходимы, чтобы приватные поля попали в JSON и в последствии в Базу данных.
    // Если свойства у entity класса приватные и нет геттеров, они не попадут в json.


    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Category getParent() {
        return parent;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
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
