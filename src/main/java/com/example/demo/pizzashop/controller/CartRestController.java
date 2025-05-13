package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;
    private final PizzaService pizzaService;

    public CartRestController(CartService cartService, PizzaService pizzaService) {
        this.cartService = cartService;
        this.pizzaService = pizzaService;
    }

    @PostMapping("/add/{pizzaId}/{size}")
    public ResponseEntity<Void> addToCart(@PathVariable Long pizzaId, @PathVariable String size) {
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if (pizza != null) {
            cartService.addToCart(pizza, size);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decrease/{pizzaId}/{size}")
    public ResponseEntity<Void> decreaseFromCart(@PathVariable Long pizzaId, @PathVariable String size) {
        cartService.removeOne(pizzaId.toString(), size);
        return ResponseEntity.ok().build();
    }
}
