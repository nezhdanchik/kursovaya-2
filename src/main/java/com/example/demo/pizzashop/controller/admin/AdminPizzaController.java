package com.example.demo.pizzashop.controller.admin;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/pizzas")
public class AdminPizzaController {

    private final PizzaService pizzaService;

    public AdminPizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public String listPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllPizzas());
        return "admin/pizza-list";
    }

    @GetMapping("/new")
    public String newPizzaForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("action", "Создание новой пиццы");
        return "admin/pizza-form";
    }

    @GetMapping("/edit/{id}")
    public String editPizzaForm(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaService.getPizzaById(id);
        model.addAttribute("pizza", pizza);
        model.addAttribute("action", "Редактирование пиццы");
        return "admin/pizza-form";
    }

    @PostMapping("/save")
    public String savePizza(@ModelAttribute Pizza pizza,
                            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        pizzaService.save(pizza, imageFile);
        return "redirect:/admin/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaService.delete(id);
        return "redirect:/admin/pizzas";
    }
}
