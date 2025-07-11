package ru.mentee.power.loop;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    private final Random random;
    private final Scanner scanner;

    // Статистика
    private int gamesPlayed = 0;
    private int minAttempts = Integer.MAX_VALUE;
    private int maxAttempts = 0;
    private int totalAttempts = 0;

    /**
     * Оригинальный конструктор для продакшена
     */
    public NumberGuessingGame() {
        this(new Scanner(System.in));
    }

    /**
     * Конструктор для тестов
     */
    public NumberGuessingGame(Scanner scanner) {
        this.scanner = scanner;
        this.random = createRandom();
    }

    protected Random createRandom() {
        return new Random();
    }

    public void startGame() {
        try {
            do {
                int attempts = playRound();
                updateStatistics(attempts);
                showStatistics();
            } while (askPlayAgain());
        } finally {
            scanner.close();
        }
    }

    public int playRound() {
        int secretNumber = random.nextInt(100) + 1;
        int attempts = 0;
        boolean guessed = false;

        System.out.println("Я загадал число от 1 до 100. Попробуйте угадать!");

        while (!guessed) {
            System.out.print("Введите ваше число: ");
            int userGuess = scanner.nextInt();
            attempts++;

            if (userGuess < secretNumber) {
                System.out.println("Ваше число меньше загаданного.");
            } else if (userGuess > secretNumber) {
                System.out.println("Ваше число больше загаданного.");
            } else {
                System.out.println("Поздравляю! Вы угадали число " + secretNumber);
                System.out.println("Вы потратили " + attempts + " попыток(и).");
                guessed = true;
            }
        }
        return attempts;
    }

    private void updateStatistics(int attempts) {
        gamesPlayed++;
        totalAttempts += attempts;
        minAttempts = Math.min(minAttempts, attempts);
        maxAttempts = Math.max(maxAttempts, attempts);
    }

    public void showStatistics() {
        if (gamesPlayed == 0) {
            System.out.println("Игр не было.");
        } else {
            double avg = (double) totalAttempts / gamesPlayed;
            System.out.println("Статистика:");
            System.out.println("Сыграно игр: " + gamesPlayed);
            System.out.println("Минимальное количество попыток: " + minAttempts);
            System.out.println("Максимальное количество попыток: " + maxAttempts);
            System.out.println("Среднее количество попыток: " + String.format("%.2f", avg));
        }
    }

    private boolean askPlayAgain() {
        System.out.print("Хотите сыграть еще раз? (да/нет): ");
        String answer = scanner.next().toLowerCase();
        return answer.equals("да") || answer.equals("yes") || answer.equals("y");
    }

    public static void main(String[] args) {
        new NumberGuessingGame().startGame();
    }
}