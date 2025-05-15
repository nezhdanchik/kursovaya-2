package com.example.demo.pizzashop.repository;

import com.example.demo.pizzashop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с заказами в базе данных.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Возвращает список заказов, принадлежащих пользователю с указанным ID.
     *
     * @param userId ID пользователя.
     * @return Список заказов пользователя.
     */
    List<Order> findByUserId(Long userId);
}