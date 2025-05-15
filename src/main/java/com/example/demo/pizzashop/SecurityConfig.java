package com.example.demo.pizzashop;

import com.example.demo.pizzashop.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс для настройки безопасности с использованием Spring Security.
 */
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Создает экземпляр конфигурации с указанием сервиса пользователей.
     *
     * @param customUserDetailsService Сервис для загрузки данных пользователя.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Настройка кодировщика паролей (BCrypt).
     *
     * @return Bean {@link PasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настройка правил доступа и формы аутентификации.
     *
     * @param http Объект HttpSecurity для настройки фильтров безопасности.
     * @return Сконфигурированный {@link SecurityFilterChain}.
     * @throws Exception если произошла ошибка при настройке.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers("/", "/about").permitAll()
                        .requestMatchers("/api/cart/**").permitAll()
                        .requestMatchers("/uploads/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}