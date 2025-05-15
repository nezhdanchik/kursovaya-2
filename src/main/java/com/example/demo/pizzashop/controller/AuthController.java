package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.User;
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
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Обрабатывает данные формы регистрации.
     *
     * @param user  Данные нового пользователя.
     * @param model Модель для передачи сообщений об ошибках.
     * @return "register" при ошибке, иначе перенаправление на страницу входа.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.userExists(user.getUsername())) {
            model.addAttribute("error", "Пользователь уже существует");
            return "register";
        }
        userService.registerUser(user.getUsername(), user.getPassword());
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