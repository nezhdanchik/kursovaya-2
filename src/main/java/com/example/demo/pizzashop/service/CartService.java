package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.CartItem;
import com.example.demo.pizzashop.model.Pizza;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Сервис для управления корзиной покупок с использованием HTTP-сессии.
 */
@Service
public class CartService {

    private final HttpSession session;

    private static final String CART_SESSION_KEY = "cart";

    /**
     * Создает экземпляр сервиса с указанной HTTP-сессией.
     *
     * @param session HTTP-сессия текущего пользователя.
     */
    public CartService(HttpSession session) {
        this.session = session;
    }

    /**
     * Получает текущую корзину из сессии (или создает новую, если её нет).
     *
     * @return Карта элементов корзины.
     */
    @SuppressWarnings("unchecked")
    private Map<String, CartItem> getCart() {
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    /**
     * Добавляет пиццу в корзину.
     *
     * @param pizza Объект пиццы.
     * @param size  Размер пиццы.
     */
    public void addToCart(Pizza pizza, String size) {
        Map<String, CartItem> cart = getCart();
        String key = pizza.getId() + "-" + size;
        cart.compute(key, (k, item) -> {
            if (item == null) {
                return new CartItem(pizza, size, 1);
            } else {
                item.setQuantity(item.getQuantity() + 1);
                return item;
            }
        });
    }

    /**
     * Уменьшает количество указанной пиццы в корзине на 1.
     *
     * @param pizzaId ID пиццы.
     * @param size    Размер пиццы.
     */
    public void removeOne(String pizzaId, String size) {
        Map<String, CartItem> cart = getCart();
        String key = pizzaId + "-" + size;
        CartItem item = cart.get(key);
        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                cart.remove(key);
            }
        }
    }

    /**
     * Возвращает список всех элементов в корзине.
     *
     * @return Список элементов корзины.
     */
    public List<CartItem> getCartItems() {
        return new ArrayList<>(getCart().values());
    }

    /**
     * Возвращает общее количество товаров в корзине.
     *
     * @return Общее количество товаров.
     */
    public int getTotalQuantity() {
        return getCart().values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    /**
     * Рассчитывает и возвращает общую стоимость товаров в корзине.
     *
     * @return Общая стоимость заказа.
     */
    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : getCart().values()) {
            BigDecimal price;
            switch (item.getSize()) {
                case "25" -> price = item.getPizza().getPrice25();
                case "30" -> price = item.getPizza().getPrice30();
                case "35" -> price = item.getPizza().getPrice35();
                default -> price = BigDecimal.ZERO;
            }

            total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return total;
    }

    /**
     * Очищает корзину.
     */
    public void clear() {
        session.removeAttribute(CART_SESSION_KEY);
    }
}