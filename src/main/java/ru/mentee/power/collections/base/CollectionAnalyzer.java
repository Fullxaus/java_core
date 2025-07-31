package ru.mentee.power.collections.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Утилитный класс для анализа коллекций.
 */
public class CollectionAnalyzer {

    /**
     * Находит все строки в коллекции, длина которых строго больше заданной.
     * Корректно обрабатывает {@code null} и пустые коллекции.
     *
     * @param strings   коллекция строк
     * @param minLength минимальная длина
     * @return список строк, длина которых строго больше {@code minLength}
     */

    // Приватный конструктор запрещает создание экземпляров класса
    private  CollectionAnalyzer() {
        throw new AssertionError("Не предназначен для создания экземпляров");
    }

    public static List<String> findLongStrings(
            final Collection<String> strings,
            final int minLength) {
        List<String> result = new ArrayList<>();
        if (strings == null || strings.isEmpty()) {
            return result;
        }
        for (String s : strings) {
            if (s != null && s.length() > minLength) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Подсчитывает количество элементов в коллекции, у которых сумма цифр
     * абсолютного значения строго больше {@code threshold}.
     * Корректно обрабатывает {@code null} и пустые коллекции.
     *
     * @param numbers   коллекция целых чисел
     * @param threshold пороговое значение для суммы цифр
     * @return количество чисел с суммой цифр больше {@code threshold}
     */
    public static int countNumbersWithDigitSumGreaterThan(
            final Collection<Integer> numbers,
            final int threshold) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (Integer num : numbers) {
            if (num != null && calculateDigitSum(num) > threshold) {
                count++;
            }
        }
        return count;
    }

    /**
     * Подсчитывает сумму цифр числа, используя абсолютное значение
     * (на случай отрицательных чисел).
     *
     * @param number целое число
     * @return сумма цифр числа
     */
    static int calculateDigitSum(final int number) {
        int sum = 0;
        int n = Math.abs(number);
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}