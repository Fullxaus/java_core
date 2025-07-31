package ru.mentee.power.loop;

import java.util.logging.Logger;

public class BreakExample {
    // Логгер для вывода сообщений вместо System.out.println
    private static final Logger logger = Logger.getLogger(BreakExample.class.getName());

    public int findFirstNegative(int[] numbers) {
        for (int number : numbers) {
            if (number < 0) {
                return number;
            }
        }
        return Integer.MAX_VALUE; // не нашли
    }

    public static void main(String[] args) {
        int[] numbers = {10, 5, 0, -3, 8, -1};
        int firstNegative = Integer.MAX_VALUE; // по-умолчанию «не найдено»

        logger.info("Ищем первое отрицательное число...");
        for (int number : numbers) {
            logger.info("Проверяем: " + number);
            if (number < 0) {
                firstNegative = number;
                logger.info("Нашли! Это " + number);
                break; // Выходим из цикла, как только нашли
            }
        }

        if (firstNegative == Integer.MAX_VALUE) {
            logger.info("Отрицательных чисел не найдено.");
        } else {
            logger.info("Первое найденное отрицательное: " + firstNegative);
        }
    }
}
