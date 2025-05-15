package com.example.demo.pizzashop.controller.admin;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.service.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер для управления пиццами в административной панели.
 */
@Controller
@RequestMapping("/admin/pizzas")
public class AdminPizzaController {

    private final PizzaService pizzaService;

    /**
     * Конструктор контроллера.
     *
     * @param pizzaService Сервис для работы с пиццами.
     */
    public AdminPizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    /**
     * Обрабатывает запрос на получение списка всех пицц.
     *
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для отображения списка пицц.
     */
    @GetMapping
    public String listPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllPizzas());
        return "admin/pizza-list";
    }

    /**
     * Обрабатывает запрос на отображение формы для создания новой пиццы.
     *
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для формы создания ��иццы.
     */
    @GetMapping("/new")
    public String newPizzaForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("action", "Создание новой пиццы");
        return "admin/pizza-form";
    }

    /**
     * Обрабатывает запрос на отображение формы для редактирования пиццы.
     *
     * @param id    Идентификатор пиццы.
     * @param model Модель для передачи данных в представление.
     * @return Имя представления для формы редактирования пиццы.
     */
    @GetMapping("/edit/{id}")
    public String editPizzaForm(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaService.getPizzaById(id);
        model.addAttribute("pizza", pizza);
        model.addAttribute("action", "Редактирование пиццы");
        return "admin/pizza-form";
    }

    /**
     * Обрабатывает запрос на сохранение пиццы.
     *
     * @param pizza     Объект пиццы для сохранения.
     * @param imageFile Файл изображения пиццы.
     * @return Перенаправление на страницу со списком пицц.
     */
    @PostMapping("/save")
    public String savePizza(@ModelAttribute Pizza pizza,
                            @RequestParam("imageFile") MultipartFile imageFile) {
        pizzaService.save(pizza, imageFile);
        return "redirect:/admin/pizzas";
    }

    /**
     * Обрабатывает запрос на удаление пиццы.
     *
     * @param id Идентификатор пиццы.
     * @return Перенаправление на страницу со списком пицц.
     */
    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaService.delete(id);
        return "redirect:/admin/pizzas";
    }
}