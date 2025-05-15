package com.example.demo.pizzashop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Класс, представляющий пиццу в меню.
 */
@Getter
@Setter
@Entity
@Table(name = "pizzas")
public class Pizza {

    /**
     * Уникальный идентификатор пиццы.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название пиццы.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание пиццы.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Ссылка на изображение пиццы.
     */
    @Column(nullable = false)
    private String imageUrl;

    /**
     * Цена пиццы размером 25 см.
     */
    @Column(nullable = false)
    private BigDecimal price25;

    /**
     * Цена пиццы размером 30 см.
     */
    @Column(nullable = false)
    private BigDecimal price30;

    /**
     * Цена пиццы размером 35 см.
     */
    @Column(nullable = false)
    private BigDecimal price35;

    /**
     * Доступность пиццы (по умолчанию — доступна).
     */
    @Column(nullable = false)
    private Boolean available = true;
}