package com.example.demo.pizzashop.service;

import com.example.demo.pizzashop.model.Pizza;
import com.example.demo.pizzashop.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с пиццами: получение, сохранение, удаление и управление изображениями.
 */
@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    /**
     * Создает экземпляр сервиса с указанием репозитория пицц.
     *
     * @param pizzaRepository Репозиторий для работы с данными пицц.
     */
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /**
     * Возвращает список всех доступных пицц (у которых {@code available = true}).
     *
     * @return Список доступных пицц.
     */
    public List<Pizza> getAllPizzas() {
        List<Pizza> allPizzas = pizzaRepository.findAll();
        List<Pizza> availablePizzas = new ArrayList<>();
        for (Pizza pizza : allPizzas) {
            if (pizza.getAvailable()) {
                availablePizzas.add(pizza);
            }
        }
        return availablePizzas;
    }

    /**
     * Возвращает пиццу по её ID.
     *
     * @param id Уникальный идентификатор пиццы.
     * @return Объект пиццы или null, если не найден.
     */
    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    /**
     * Сохраняет или обновляет данные о пицце, включая загрузку нового изображения.
     *
     * @param pizza     Объект пиццы с новыми данными.
     * @param imageFile Загруженный файл изображения (может быть null).
     */
    public void save(Pizza pizza, MultipartFile imageFile) {
        Pizza existing;

        if (pizza.getId() != null) {
            existing = pizzaRepository.findById(pizza.getId()).orElse(pizza);
        } else {
            existing = pizza;
        }

        if (pizza.getName() != null) {
            existing.setName(pizza.getName());
        }
        if (pizza.getDescription() != null) {
            existing.setDescription(pizza.getDescription());
        }
        if (pizza.getPrice25() != null) {
            existing.setPrice25(pizza.getPrice25());
        }
        if (pizza.getPrice30() != null) {
            existing.setPrice30(pizza.getPrice30());
        }
        if (pizza.getPrice35() != null) {
            existing.setPrice35(pizza.getPrice35());
        }

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(filename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                existing.setImageUrl(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pizzaRepository.save(existing);
    }

    /**
     * Помечает пиццу как недоступную и удаляет её изображение с диска.
     *
     * @param id Идентификатор пиццы для удаления.
     */
    public void delete(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElse(null);
        if (pizza == null) {
            return;
        }
        if (pizza.getImageUrl() != null) {
            try {
                Path imagePath = Paths.get("uploads").resolve(pizza.getImageUrl());
                Files.deleteIfExists(imagePath);
                System.out.println("Файл удалён: " + imagePath);
            } catch (IOException e) {
                System.err.println("Не удалось удалить файл изображения: " + pizza.getImageUrl());
                e.printStackTrace();
            }
        }
        pizza.setAvailable(false);
        pizzaRepository.save(pizza);
    }
}