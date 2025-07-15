package ru.mentee.power.collections.base;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class CollectionAnalyzer {

    /**
     * Находит все строки в коллекции, длина которых строго больше заданной.
     * Корректно обрабатывает null и пустые коллекции.
     *
     * @param strings Коллекция строк
     * @param minLength Минимальная длина
     * @return Список строк, длина которых строго больше minLength
     */
    public static List<String> findLongStrings(Collection<String> strings, int minLength) {
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
     * Подсчитывает количество элементов в коллекции, у которых сумма цифр абсолютного значения
     * строго больше threshold. Корректно обрабатывает null и пустые коллекции.
     *
     * @param numbers Коллекция целых чисел
     * @param threshold Пороговое значение для суммы цифр
     * @return Количество чисел с суммой цифр больше threshold
     */
    public static int countNumbersWithDigitSumGreaterThan(Collection<Integer> numbers, int threshold) {
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
     * Подсчитывает сумму цифр числа, используя абсолютное значение (на случай отрицательных чисел).
     *
     * @param number Целое число
     * @return Сумма цифр числа
     */
    static int calculateDigitSum(int number) {
        int sum = 0;
        int n = Math.abs(number);
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}
