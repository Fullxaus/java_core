package ru.mentee.power.loop;

import java.util.logging.Logger;

public class ForEachLoopExample {

    // Логгер для вывода сообщений
    private static final Logger logger =
            Logger.getLogger(ForEachLoopExample.class.getName());

    public static void main(String[] args) {
        String[] fruits = {"Яблоко", "Банан", "Апельсин"};

        logger.info("Фрукты (без индекса):");
        // Перебираем массив с помощью for-each
        for (String fruit : fruits) {
            logger.info(fruit);
        }
    }
}
