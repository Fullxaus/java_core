package ru.mentee.power.loop;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NestedLoopExample {

    // Логгер для вывода сообщений
    private static final Logger logger =
            Logger.getLogger(NestedLoopExample.class.getName());

    public static void main(String[] args) {
        // Вывод заголовка через Supplier для ленивой оценки
        logger.log(Level.INFO, () -> "Пары координат:");

        // Внешний цикл по X
        for (int x = 1; x <= 2; x++) {
            // Внутренний цикл по Y
            for (int y = 1; y <= 3; y++) {
                final int fx = x, fy = y;
                // Ленивый вызов String.format
                logger.log(Level.INFO, () -> String.format("X=%d, Y=%d", fx, fy));
            }
            // Разделитель для наглядности
            logger.log(Level.INFO, () -> "--- Следующий X ---");
        }
    }
}

