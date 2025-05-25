package com.example.demo.pizzashop.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для формы регистрации.
 */
@Getter
@Setter
public class RegistrationForm {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
