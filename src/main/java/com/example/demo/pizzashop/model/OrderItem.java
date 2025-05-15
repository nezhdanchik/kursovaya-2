package com.example.demo.pizzashop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий отдельный элемент заказа (конкретная пицца и её количество).
 */
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    /**
     * Уникальный идентификатор элемента заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ссылка на заказ, к которому относится этот элемент.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Пицца, входящая в заказ.
     */
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    /**
     * Размер пиццы (например: "small", "medium", "large").
     */
    @Column(nullable = false)
    private String size;

    /**
     * Количество пицц данного вида и размера.
     */
    @Column(nullable = false)
    private int quantity;
}