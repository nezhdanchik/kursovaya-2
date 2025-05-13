package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.CartItem;
import com.example.demo.pizzashop.model.User;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    private final PizzaService pizzaService;
    private final CartService cartService;

    public MainPageController(PizzaService pizzaService, CartService cartService) {
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String showMainPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("pizzas", pizzaService.getAllPizzas());
        model.addAttribute("cartCount", cartService.getTotalQuantity());
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("role", user.getRole());
        }

        Map<String, Integer> quantities = cartService.getCartItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getPizza().getId() + "-" + item.getSize(),
                        CartItem::getQuantity
                ));
        model.addAttribute("quantities", quantities);

        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
