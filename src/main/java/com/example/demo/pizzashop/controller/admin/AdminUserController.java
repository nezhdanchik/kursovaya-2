package com.example.demo.pizzashop.controller.admin;

import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления пользователями в административной панели.
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    /**
     * Конструктор контроллера.
     *
     * @param userService Сервис для работы с пользователями.
     */
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает список всех пользователей.
     *
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для отображения списка пользователей.
     */
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    /**
     * Обновляет роль пользователя.
     *
     * @param id   Идентификатор пользователя.
     * @param role Новая роль пользователя.
     * @return Перенаправление на страницу со списком пользователей.
     */
    @PostMapping("/{id}/role")
    public String updateUserRole(@PathVariable Long id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/admin/users";
    }

    /**
     * Удаляет пользователя.
     *
     * @param id Идентификатор пользователя.
     * @return Перенаправление на страницу со списком пользователей.
     */
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}