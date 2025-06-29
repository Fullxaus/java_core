package ru.mentee.power.conditions;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class LogicalTrainer {

    /**
     * Выполняет логические проверки на основе предпочтений пользователя.
     *
     * @param likeProgramming Любит ли программирование.
     * @param likeMath        Любит ли математику.
     * @param likeReading     Любит ли читать книги.
     * @return Map, где ключ - номер утверждения, значение - результат (true/false).
     */
    public static Map<Integer, Boolean> evaluateLogic(boolean likeProgramming, boolean likeMath, boolean likeReading) {
        Map<Integer, Boolean> results = new HashMap<>();

        results.put(1, likeProgramming && likeMath);
        results.put(2, likeProgramming || likeReading);
        results.put(3, likeMath && !likeReading);
        results.put(4, !likeProgramming && !likeMath);
        results.put(5, likeProgramming || likeMath || likeReading);

        int dislikes = 0;
        if (!likeProgramming) dislikes++;
        if (!likeMath) dislikes++;
        if (!likeReading) dislikes++;
        results.put(6, dislikes == 2);

        return results;
    }

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

        Map<Integer, Boolean> results = evaluateLogic(likeProgramming, likeMath, likeReading);

        System.out.println("\nРезультаты проверок:");
        System.out.println("Утверждение 1: Вы любите и программирование, и математику: " + results.get(1));
        System.out.println("Утверждение 2: Вы любите программирование или чтение книг: " + results.get(2));
        System.out.println("Утверждение 3: Вы любите математику, но не любите читать книги: " + results.get(3));
        System.out.println("Утверждение 4: Вы не любите ни программирование, ни математику: " + results.get(4));
        System.out.println("Утверждение 5: Вы любите хотя бы одно из трех: " + results.get(5));
        System.out.println("Утверждение 6: Вы не любите ровно две вещи из трех: " + results.get(6));

        scanner.close();
    }
}
