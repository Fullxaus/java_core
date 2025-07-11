package ru.mentee.power.loop;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberGuessingGameTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testPlayRoundGuessCorrectly() {
        // Готовим входные данные
        String inputData = String.join(System.lineSeparator(),
                "50",
                "30",
                "40",
                "42",
                "нет"
        ) + System.lineSeparator();
        Scanner testScanner = new Scanner(inputData);

        // Конструируем тестовую игру с предсказуемым Random и тестовым Scanner
        NumberGuessingGame game = new TestableNumberGuessingGame(42, testScanner);

        // Запускаем игру
        game.startGame();

        String output = out.toString();

        assertThat(output).contains("Я загадал число от 1 до 100. Попробуйте угадать!");
        assertThat(output).contains("Ваше число меньше загаданного.");
        assertThat(output).contains("Ваше число больше загаданного.");
        assertThat(output).contains("Поздравляю! Вы угадали число 42");
        assertThat(output).contains("Вы потратили 4 попыток(и).");
    }

    @Test
    void testStatisticsUpdated() {
        String inputData = String.join(System.lineSeparator(),
                "30", "40", "42", "да",
                "60", "50", "40", "45", "42", "нет"
        ) + System.lineSeparator();
        Scanner testScanner = new Scanner(inputData);
        NumberGuessingGame game = new TestableNumberGuessingGame(42, testScanner);

        game.startGame();
        String output = out.toString();

        assertThat(output).contains("Сыграно игр: 2");
        assertThat(output).contains("Минимальное количество попыток: 3");
        assertThat(output).contains("Максимальное количество попыток: 5");
        assertThat(output).contains("Среднее количество попыток: 4.00");
    }

    // Тестовый подкласс, принимающий Scanner и возвращающий фиксированное Random
    static class TestableNumberGuessingGame extends NumberGuessingGame {
        private final int fixedValue;
        private final Scanner scanner;

        TestableNumberGuessingGame(int fixedValue, Scanner scanner) {
            super(scanner);
            this.fixedValue = fixedValue;
            this.scanner = scanner;
        }

        @Override
        protected Random createRandom() {
            return new Random() {
                @Override
                public int nextInt(int bound) {
                    return fixedValue - 1;
                }
            };
        }
    }
}