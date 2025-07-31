package ru.mentee.power.loop;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ForLoopExample {

    // Логгер для вывода сообщений
    private static final Logger logger =
            Logger.getLogger(ForLoopExample.class.getName());

    public static void main(String[] args) {
        // Объявляем массив строк
        String[] fruits = {"Яблоко", "Банан", "Апельсин"};

        // Выводим заголовок через logger.log с Supplier, чтобы строка строилась только при включенном уровне
        logger.log(Level.INFO, () -> "Фрукты (с индексом):");

        // Перебираем массив по индексам
        for (int index = 0; index < fruits.length; index++) {
            final int idx = index;
            final String fruit = fruits[index];
            // Используем logger.log с Supplier для ленивой оценки строки
            logger.log(Level.INFO, () -> String.format("Индекс %d: %s", idx, fruit));
        }
    }
}


