package ru.mentee.power.loop;

import java.util.List;
import java.util.logging.Logger;

public class ContinueExample {
    // Логгер для вывода сообщений вместо System.out.println
    private static final Logger logger = Logger.getLogger(ContinueExample.class.getName());

    public int sumOfPositives(List<Integer> data) {
        if (data == null) {
            throw new NullPointerException("Список null");
        }

        int sumOfPositives = 0;
        for (int value : data) {
            if (value <= 0) {
                continue;
            }
            sumOfPositives += value;
        }
        return sumOfPositives;
    }

    public static void main(String[] args) {
        List<Integer> data = List.of(5, -2, 10, 0, -8, 3);
        int sumOfPositives = 0;

        logger.info("Суммируем только положительные числа...");
        for (int value : data) {
            if (value <= 0) {
                logger.info("Пропускаем: " + value);
                continue; // Переходим к следующему числу, если текущее не положительное
            }
            logger.info("Добавляем: " + value);
            sumOfPositives += value;
        }

        logger.info("Сумма положительных: " + sumOfPositives);
    }
}
