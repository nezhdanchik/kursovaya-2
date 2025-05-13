package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.CartItem;
import com.example.demo.pizzashop.model.Pizza;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartService {

    private final HttpSession session;

    private static final String CART_SESSION_KEY = "cart";

    public CartService(HttpSession session) {
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    private Map<String, CartItem> getCart() {
        Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

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

    public List<CartItem> getCartItems() {
        return new ArrayList<>(getCart().values());
    }

    public int getTotalQuantity() {
        return getCart().values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

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


    public void clear() {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
