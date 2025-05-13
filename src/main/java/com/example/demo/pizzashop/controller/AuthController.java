package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.userExists(user.getUsername())) {
            model.addAttribute("error", "Пользователь уже существует");
            return "register";
        }
        userService.registerUser(user.getUsername(), user.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "login";
    }

}
