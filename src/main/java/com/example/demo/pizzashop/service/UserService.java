package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с пользователями: регистрация, управление и проверка существования.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

    public Boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Список пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Обновляет роль пользователя.
     *
     * @param id   Идентификатор пользователя.
     * @param role Новая роль.
     */
    public void updateUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}