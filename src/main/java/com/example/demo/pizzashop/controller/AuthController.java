package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.dto.RegistrationForm;
import com.example.demo.pizzashop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления регистрацией и входом пользователей.
 */
@Controller
public class AuthController {

    private final UserService userService;

    /**
     * Создает экземпляр контроллера с указанным сервисом пользователей.
     *
     * @param userService Сервис для работы с пользователями.
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает форму регистрации.
     *
     * @param model Модель для передачи данных в представление.
     * @return Имя шаблона "register".
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("form") RegistrationForm form, Model model) {
        if (userService.userExists(form.getUsername())) {
            model.addAttribute("error", "Пользователь уже существует");
            return "register";
        }

        if (userService.emailExists(form.getEmail())) {
            model.addAttribute("error", "Этот email уже используется");
            return "register";
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("error", "Пароли не совпадают");
            return "register";
        }

        userService.registerUser(form.getUsername(), form.getPassword(), form.getEmail());
        return "redirect:/login";
    }

    /**
     * Отображает форму входа.
     *
     * @param error Сообщение об ошибке (если есть).
     * @param model Модель для передачи данных в представление.
     * @return Имя шаблона "login".
     */
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "login";
    }
}