package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.CartItem;
import com.example.demo.pizzashop.model.Order;
import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с заказами: оформление и просмотр.
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    /**
     * Создает экземпляр контроллера с указанными сервисами.
     *
     * @param orderService Сервис для работы с заказами.
     * @param cartService  Сервис для работы с корзиной.
     */
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    /**
     * Обрабатывает оформление заказа.
     *
     * @param address Адрес доставки из формы.
     * @param user    Текущий авторизованный пользователь.
     * @param model   Модель для передачи данных в представление.
     * @return Перенаправление на страницу заказов или корзины при ошибке.
     */
    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String address, @AuthenticationPrincipal User user, Model model) {
        if (user == null) return "redirect:/login";

        List<CartItem> items = cartService.getCartItems();
        if (items.isEmpty()) {
            model.addAttribute("message", "Корзина пуста");
            return "cart";
        }

        orderService.createOrder(user, address, items);
        cartService.clear();
        return "redirect:/orders/my";
    }

    /**
     * Отображает список заказов пользователя с фильтрацией и сортировкой.
     *
     * @param user         Текущий авторизованный пользователь.
     * @param status       Статус заказа для фильтрации (по умолчанию — все).
     * @param sort         Параметр сортировки (по умолчанию — newest).
     * @param model        Модель для передачи данных в представление.
     * @return Имя шаблона "orders".
     */
    @GetMapping("/my")
    public String myOrders(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(defaultValue = "newest") String sort,
            Model model
    ) {
        List<Order> orders = orderService.getFilteredAndSortedOrders(user, status, sort);

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("sort", sort);

        return "orders";
    }
}