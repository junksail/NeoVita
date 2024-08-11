package com.example.neovito.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Product {
    private long id;
    private String title;
    private String description;
    private int price;
    private String city;
    private String author;
}
