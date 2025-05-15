package com.example.demo.pizzashop.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс, представляющий пользователя системы.
 * Реализует интерфейс {@link UserDetails} для интеграции с Spring Security.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Логин пользователя (уникальный).
     */
    @Setter
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Пароль пользователя.
     */
    @Setter
    @Column(nullable = false)
    private String password;

    /**
     * Роль пользователя (например: "USER", "ADMIN").
     */
    @Setter
    @Getter
    @Column(nullable = false)
    private String role;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает список прав (ролей) пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    /**
     * Проверяет, не истёк ли срок действия аккаунта.
     * @return true — аккаунт не истёк.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирован ли аккаунт.
     * @return true — аккаунт не заблокирован.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истёк ли срок действия учетных данных.
     * @return true — учетные данные не истекли.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, включен ли аккаунт.
     * @return true — аккаунт включён.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}