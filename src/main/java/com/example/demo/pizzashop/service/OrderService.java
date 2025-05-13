package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.*;
import com.example.demo.pizzashop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void createOrder(User user, String address, List<CartItem> cartItems) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setDate(new Date());
        order.setStatus("Создан");

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setPizza(cartItem.getPizza());
            item.setQuantity(cartItem.getQuantity());
            item.setSize(cartItem.getSize());

            // Вычисляем цену по размеру
            BigDecimal price = switch (cartItem.getSize()) {
                case "25" -> cartItem.getPizza().getPrice25();
                case "30" -> cartItem.getPizza().getPrice30();
                case "35" -> cartItem.getPizza().getPrice35();
                default -> BigDecimal.ZERO;
            };

            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            items.add(item);
        }

        order.setOrderItems(items);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
    }


    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void updateStatus(Long id, String status) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    public List<Order> getFilteredAndSortedOrders(User user, String status, String sortOrder) {
        // Получаем заказы: либо все, либо только пользователя
        List<Order> orders = (user == null)
                ? orderRepository.findAll()
                : orderRepository.findByUserId(user.getId());

        // Фильтруем по статусу, если нужно
        if (!"all".equalsIgnoreCase(status)) {
            orders = orders.stream()
                    .filter(order -> status.equalsIgnoreCase(order.getStatus()))
                    .toList();
        }

        // Сортируем по нужному признаку
        Comparator<Order> comparator = switch (sortOrder.toLowerCase()) {
            case "newest" -> Comparator.comparing(Order::getDate).reversed();
            case "oldest" -> Comparator.comparing(Order::getDate);
            case "cheapest" -> Comparator.comparing(Order::getTotalPrice);
            case "most_expensive" -> Comparator.comparing(Order::getTotalPrice).reversed();
            default -> Comparator.comparing(Order::getDate).reversed(); // по умолчанию: newest
        };

        return orders.stream()
                .sorted(comparator)
                .toList();
    }

}