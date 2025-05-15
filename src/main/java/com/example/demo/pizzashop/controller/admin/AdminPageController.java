package com.example.demo.pizzashop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для отображения главной страницы административной панели.
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController {

    /**
     * Обрабатывает запрос на отображение главной страницы административной панели.
     *
     * @return Имя представления для главной страницы административной панели.
     */
    @GetMapping
    public String adminHome() {
        return "admin/index";
    }
}