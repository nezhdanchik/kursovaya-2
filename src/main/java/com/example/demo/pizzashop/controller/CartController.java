package com.example.demo.pizzashop.controller;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.service.CartService;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с корзиной покупок.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    /**
     * Создает экземпляр контроллера с указанным сервисом корзины.
     *
     * @param cartService  Сервис для управления корзиной.
     * @param pizzaService Сервис для работы с пиццами (не используется в данном классе).
     */
    public CartController(CartService cartService, PizzaService pizzaService) {
        this.cartService = cartService;
    }

    /**
     * Отображает содержимое корзины.
     *
     * @param model Модель для передачи данных в представление.
     * @return Имя шаблона "cart".
     */
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("cartCount", cartService.getTotalQuantity());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart";
    }

    /**
     * Обрабатывает оформление заказа.
     *
     * @param address Адрес доставки, переданный из формы.
     * @param model   Модель для передачи сообщения об успешном заказе.
     * @return Перенаправление на главную страницу.
     */
    @PostMapping("/checkout")
    public String checkout(@RequestParam String address, Model model) {
        cartService.clear();
        model.addAttribute("message", "Заказ оформлен на адрес: " + address);
        return "redirect:/";
    }
}