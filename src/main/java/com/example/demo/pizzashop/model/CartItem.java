package com.example.demo.pizzashop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private Pizza pizza;
    private String size;
    private int quantity;
}
