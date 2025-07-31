package ru.mentee.power.loop;

import java.util.Scanner;
import java.util.logging.Logger;

public class DoWhileLoopExample {
    // Логгер для вывода сообщений вместо System.out.println
    private static final Logger logger = Logger.getLogger(DoWhileLoopExample.class.getName());

    public int repeatAction(String[] answers) {
        int count = 0;
        for (String answer : answers) {
            if (!answer.equalsIgnoreCase("да")) {
                break;
            }
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String answer;

        do {
            logger.info("Выполняем важное действие...");
            logger.info("Повторить? (да/нет): ");
            answer = scanner.nextLine();
        } while (answer.equalsIgnoreCase("да"));

        logger.info("Завершение.");
        scanner.close();
    }
}
