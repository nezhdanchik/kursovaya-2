package com.example.demo.pizzashop.repository;

import com.example.demo.pizzashop.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с пиццами в базе данных.
 */
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}