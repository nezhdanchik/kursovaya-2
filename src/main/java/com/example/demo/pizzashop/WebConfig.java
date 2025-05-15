package com.example.demo.pizzashop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурационный класс для настройки статических ресурсов.
 * Предоставляет доступ к загруженным файлам (например, изображениям) через URL.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Настраивает обработку статических ресурсов.
     * Папка "uploads" становится доступной по адресу "/uploads/**".
     *
     * @param registry Регистр обработчиков ресурсов.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}