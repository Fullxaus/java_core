package ru.mentee.power.loop;

import java.util.List;
import java.util.logging.Logger;

public class ForEachListExample {

    // Логгер для вывода сообщений
    private static final Logger logger =
            Logger.getLogger(ForEachListExample.class.getName());

    public static void main(String[] args) {
        // Создаем иммутабельный список (начиная с Java 9)
        List<String> colors = List.of("Красный", "Зеленый", "Синий");

        logger.info("Цвета:");
        for (String color : colors) {
            logger.info(color);
        }
    }
}
