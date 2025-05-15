package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер для работы с корзиной через API.
 */
@RestController
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;
    private final PizzaService pizzaService;

    /**
     * Создает экземпляр контроллера с указанными сервисами.
     *
     * @param cartService  Сервис корзины.
     * @param pizzaService Сервис пицц.
     */
    public CartRestController(CartService cartService, PizzaService pizzaService) {
        this.cartService = cartService;
        this.pizzaService = pizzaService;
    }

    /**
     * Добавляет пиццу в корзину.
     *
     * @param pizzaId ID пиццы.
     * @param size    Размер пиццы.
     * @return Ответ с кодом 200 OK.
     */
    @PostMapping("/add/{pizzaId}/{size}")
    public ResponseEntity<Void> addToCart(@PathVariable Long pizzaId, @PathVariable String size) {
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if (pizza != null) {
            cartService.addToCart(pizza, size);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Уменьшает количество указанной пиццы в корзине на 1.
     *
     * @param pizzaId ID пиццы.
     * @param size    Размер пиццы.
     * @return Ответ с кодом 200 OK.
     */
    @PostMapping("/decrease/{pizzaId}/{size}")
    public ResponseEntity<Void> decreaseFromCart(@PathVariable Long pizzaId, @PathVariable String size) {
        cartService.removeOne(pizzaId.toString(), size);
        return ResponseEntity.ok().build();
    }
}