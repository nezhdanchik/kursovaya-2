package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.CartItem;
import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер для отображения главной страницы и информации о сайте.
 */
@Controller
public class MainPageController {

    private final PizzaService pizzaService;
    private final CartService cartService;

    /**
     * Создает экземпляр контроллера с указанными сервисами.
     *
     * @param pizzaService Сервис для работы с пиццами.
     * @param cartService  Сервис для работы с корзиной.
     */
    public MainPageController(PizzaService pizzaService, CartService cartService) {
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }

    /**
     * Отображает главную страницу с пиццами и данными корзины.
     *
     * @param model Модель для передачи данных в представление.
     * @param user  Текущий авторизованный пользователь.
     * @return Имя шаблона "main".
     */
    @GetMapping("/")
    public String showMainPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("pizzas", pizzaService.getAllPizzas());
        model.addAttribute("cartCount", cartService.getTotalQuantity());
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("role", user.getRole());
        }

        // ключ — строка вида "id-размер", значение — количество пиццы такого типа
        Map<String, Integer> quantities = new HashMap<>();

        // Получаем все элементы корзины
        for (CartItem item : cartService.getCartItems()) {
            String key = item.getPizza().getId() + "-" + item.getSize();
            quantities.put(key, item.getQuantity());
        }

        model.addAttribute("quantities", quantities);

        return "main";
    }

    /**
     * Отображает страницу "О сайте".
     *
     * @return Имя шаблона "about".
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}