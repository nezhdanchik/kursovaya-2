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

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

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

