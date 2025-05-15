package com.example.demo.pizzashop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс, представляющий элемент корзины — пиццу с указанием размера и количества.
 */
@Data
@AllArgsConstructor
public class CartItem {
    /**
     * Объект пиццы.
     */
    private Pizza pizza;

    /**
     * Размер пиццы (например, "small", "medium", "large").
     */
    private String size;

    /**
     * Количество пицц данного вида и размера.
     */
    private int quantity;
}