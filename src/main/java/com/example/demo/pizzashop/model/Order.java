package com.example.demo.pizzashop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Класс, представляющий заказ в системе.
 */
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    /**
     * Уникальный идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Статус заказа (например: "в обработке", "доставляется", "завершён").
     */
    @Column(nullable = false)
    private String status;

    /**
     * Дата и время оформления заказа.
     */
    @Column(nullable = false)
    private Date date;

    /**
     * Адрес доставки.
     */
    @Column(nullable = false)
    private String address;

    /**
     * Общая стоимость заказа.
     */
    @Column(nullable = false)
    private BigDecimal totalPrice;

    /**
     * Пользователь, сделавший заказ.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Список товаров (пицц) в заказе.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}