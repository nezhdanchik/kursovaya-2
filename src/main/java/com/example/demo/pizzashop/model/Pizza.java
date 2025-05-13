package com.example.demo.pizzashop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private BigDecimal price25;

    @Column(nullable = false)
    private BigDecimal price30;

    @Column(nullable = false)
    private BigDecimal price35;

    @Column(nullable = false)
    private Boolean available = true;
}
