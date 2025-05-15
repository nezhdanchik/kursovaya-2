package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с пользователями: регистрация и проверка существования.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создает экземпляр сервиса с указанием репозитория и кодировщика паролей.
     *
     * @param userRepository   Репозиторий для работы с данными пользователей.
     * @param passwordEncoder  Кодировщик паролей.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрирует нового пользователя с ролью "ROLE_USER".
     *
     * @param username Логин пользователя.
     * @param password Пароль пользователя.
     */
    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    /**
     * Проверяет, существует ли пользователь с таким логином.
     *
     * @param username Логин для проверки.
     * @return true — если пользователь существует, иначе false.
     */
    public Boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}