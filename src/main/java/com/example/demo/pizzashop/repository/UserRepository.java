package com.example.demo.pizzashop.repository;

import com.example.demo.pizzashop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с пользователями в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его логину.
     *
     * @param username Логин пользователя.
     * @return Объект пользователя или {@code null}, если пользователь не найден.
     */
    User findByUsername(String username);
}