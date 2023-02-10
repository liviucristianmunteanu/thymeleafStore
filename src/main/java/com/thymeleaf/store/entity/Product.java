package com.thymeleaf.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imageUrl;
    private String name;

    private String description;
    private float price;

    private Integer quantity;

    public Product(String imageUrl,String name,String description, float price, Integer quantity) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
