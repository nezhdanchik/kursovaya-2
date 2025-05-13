package com.example.demo.pizzashop.controller.admin;

import com.example.demo.pizzashop.model.Order;
import com.example.demo.pizzashop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String allOrders(
            @RequestParam(defaultValue = "all") String status,
            @RequestParam(defaultValue = "newest") String sort,
            Model model
    ) {
        List<Order> orders = orderService.getFilteredAndSortedOrders(null, status, sort);
        BigDecimal sumPrice = BigDecimal.valueOf(0);
        for (Order order : orders) {
            sumPrice = sumPrice.add(order.getTotalPrice());
        }
        model.addAttribute("sumPrice", sumPrice);
        BigDecimal avgPrice = sumPrice.divide(BigDecimal.valueOf(orders.size()), BigDecimal.ROUND_HALF_UP);
        model.addAttribute("avgPrice", avgPrice);

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("sort", sort);

        return "admin/orders";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String newStatus) {
        orderService.updateStatus(id, newStatus);
        return "redirect:/admin/orders";
    }
}

