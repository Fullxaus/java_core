package ru.mentee.power.methods.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс с рекурсивными методами для решения различных задач
 */
public class RecursionExercises {

    /**
     * Вычисляет факториал числа n
     *
     * @param n Положительное целое число
     * @return Факториал числа n
     * @throws IllegalArgumentException если n < 0
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /**
     * Вычисляет n-ое число в последовательности Фибоначчи
     *
     * @param n Позиция в последовательности Фибоначчи (0-based)
     * @return Число Фибоначчи на позиции n
     * @throws IllegalArgumentException если n < 0
     */
    public static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Оптимизированный метод для вычисления n-ого числа Фибоначчи
     *
     * @param n Позиция в последовательности Фибоначчи (0-based)
     * @return Число Фибоначчи на позиции n
     * @throws IllegalArgumentException если n < 0
     */
    public static long fibonacciOptimized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        // Используем мемоизацию:
        Map<Integer, Long> memo = new HashMap<>();
        return fibMemo(n, memo);
    }

    private static long fibMemo(int n, Map<Integer, Long> memo) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        long result = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    /**
     * Проверяет, является ли строка палиндромом
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String must not be null");
        }
        // 1) Убираем всё, что не буквы и не цифры, 2) приводим к нижнему регистру
        String cleaned = str
                .replaceAll("[^\\p{L}\\p{Nd}]", "") // \p{L} — любая буква, \p{Nd} — цифры
                .toLowerCase();

        return isPalHelper(cleaned, 0, cleaned.length() - 1);
    }


    private static boolean isPalHelper(String s, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        return isPalHelper(s, left + 1, right - 1);
    }

    /**
     * Вычисляет сумму цифр числа
     *
     * @param n Целое число
     * @return Сумма цифр числа
     */
    public static int sumOfDigits(int n) {
        n = Math.abs(n);
        if (n < 10) {
            return n;
        }
        return (n % 10) + sumOfDigits(n / 10);
    }

    /**
     * Возводит число в степень
     *
     * @param base Основание
     * @param exponent Показатель степени
     * @return Результат возведения в степень
     */
    public static double power(double base, int exponent) {
        if (exponent == 0) {
            return 1.0;
        }
        if (exponent > 0) {
            return base * power(base, exponent - 1);
        } else {
            // отрицательная степень
            return 1.0 / power(base, -exponent);
        }
    }

    /**
     * Находит наибольший общий делитель двух чисел
     *
     * @param a Первое число
     * @param b Второе число
     * @return Наибольший общий делитель
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * Обращает порядок элементов в массиве
     *
     * @param array Исходный массив
     * @param start Начальный индекс для обработки
     * @param end Конечный индекс для обработки
     */
    public static void reverseArray(int[] array, int start, int end) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (start >= end) {
            return;
        }
        // поменять местами
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;
        // рекурсивный вызов
        reverseArray(array, start + 1, end - 1);
    }
}
