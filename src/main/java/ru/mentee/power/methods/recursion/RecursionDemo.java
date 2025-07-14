package ru.mentee.power.methods.recursion;

import java.util.Arrays;

public class RecursionDemo {

    public static void main(String[] args) {
        System.out.println("=== Факториал ===");
        System.out.println("5! = " + RecursionExercises.factorial(5));

        System.out.println("\n=== Числа Фибоначчи ===");
        System.out.println("Наивная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(RecursionExercises.fibonacci(i) + " ");
        }

        System.out.println("\nОптимизированная реализация:");
        for (int i = 0; i <= 10; i++) {
            System.out.print(RecursionExercises.fibonacciOptimized(i) + " ");
        }

        System.out.println("\n\n=== Проверка палиндрома ===");
        String[] tests = {
                "",
                "madam",
                "racecar",
                "hello",
                "А роза упала на лапу Азора"
        };
        for (String s : tests) {
            System.out.printf("\"%s\" -> %b%n", s, RecursionExercises.isPalindrome(s));
        }

        System.out.println("\n=== Сумма цифр ===");
        int number = 12345;
        System.out.printf("Сумма цифр %d = %d%n",
                number, RecursionExercises.sumOfDigits(number));

        System.out.println("\n=== Возведение в степень ===");
        double base = 2;
        int exp1 = 10, exp2 = -3;
        System.out.printf("%.1f^%d = %.1f%n", base, exp1,
                RecursionExercises.power(base, exp1));
        System.out.printf("%.1f^%d = %.5f%n", base, exp2,
                RecursionExercises.power(base, exp2));

        System.out.println("\n=== Наибольший общий делитель ===");
        int a = 48, b = 36;
        System.out.printf("gcd(%d, %d) = %d%n",
                a, b, RecursionExercises.gcd(a, b));

        System.out.println("\n=== Обращение массива ===");
        int[] array = {1, 2, 3, 4, 5, 6};
        System.out.println("Исходный:  " + Arrays.toString(array));
        RecursionExercises.reverseArray(array, 0, array.length - 1);
        System.out.println("Перевернутый: " + Arrays.toString(array));
    }
}
