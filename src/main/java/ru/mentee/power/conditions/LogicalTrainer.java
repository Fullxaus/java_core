package ru.mentee.power.conditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Логический тренажер, который выполняет проверки на основе
 * предпочтений пользователя.
 */
public class LogicalTrainer {

    /**
     * Выполняет логические проверки на основе предпочтений пользователя.
     *
     * @param likeProgramming любит ли пользователь программирование
     * @param likeMath        любит ли пользователь математику
     * @param likeReading     любит ли пользователь чтение книг
     * @return карта, где ключ — номер утверждения (1–6),
     *         а значение — результат проверки (true/false)
     */
    public static Map<Integer, Boolean> evaluateLogic(boolean likeProgramming,
                                                      boolean likeMath,
                                                      boolean likeReading) {
        Map<Integer, Boolean> results = new HashMap<>();

        results.put(1, likeProgramming && likeMath);
        results.put(2, likeProgramming || likeReading);
        results.put(3, likeMath && !likeReading);
        results.put(4, !likeProgramming && !likeMath);
        results.put(5, likeProgramming || likeMath || likeReading);

        int dislikes = 0;
        if (!likeProgramming) {
            dislikes++;
        }
        if (!likeMath) {
            dislikes++;
        }
        if (!likeReading) {
            dislikes++;
        }
        results.put(6, dislikes == 2);

        return results;
    }

    /**
     * Точка входа в приложение.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в логический тренажер!");
        System.out.println("Ответьте на несколько вопросов:");

        System.out.print("Вы любите программирование? (true/false): ");
        boolean likeProgramming = scanner.nextBoolean();

        System.out.print("Вы любите математику? (true/false): ");
        boolean likeMath = scanner.nextBoolean();

        System.out.print("Вы любите читать книги? (true/false): ");
        boolean likeReading = scanner.nextBoolean();

        Map<Integer, Boolean> results = evaluateLogic(
                likeProgramming,
                likeMath,
                likeReading
        );

        System.out.println("\nРезультаты проверок:");
        System.out.printf("Утверждение 1: Вы любите и программирование, и математику: %b%n",
                results.get(1));
        System.out.printf("Утверждение 2: Вы любите программирование или чтение книг: %b%n",
                results.get(2));
        System.out.printf("Утверждение 3: Вы любите математику, но не любите читать книги: %b%n",
                results.get(3));
        System.out.printf("Утверждение 4: Вы не любите ни программирование, ни математику: %b%n",
                results.get(4));
        System.out.printf("Утверждение 5: Вы любите хотя бы одно из трёх: %b%n",
                results.get(5));
        System.out.printf("Утверждение 6: Вы не любите ровно две вещи из трёх: %b%n",
                results.get(6));

        scanner.close();
    }
}