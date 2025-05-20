package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserDetailsService} для загрузки данных пользователя по логину.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Создает экземпляр сервиса с указанием репозитория пользователей.
     *
     * @param userRepository Репозиторий для работы с данными пользователей.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает пользователя по его логину.
     *
     * @param username Логин пользователя.
     * @return Объект {@link UserDetails}, представляющий пользователя.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}