package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService, PizzaService pizzaService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("cartCount", cartService.getTotalQuantity());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String address, Model model) {
        cartService.clear();
        model.addAttribute("message", "Заказ оформлен на адрес: " + address);
        return "redirect:/";
    }
}
