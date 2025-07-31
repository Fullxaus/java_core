package ru.mentee.power.collections.base;

import java.util.ArrayList;
import java.util.List;

public final class NumberFilter {

    // Приватный конструктор запрещает создание экземпляров класса
    private NumberFilter() {
        throw new AssertionError("Не предназначен для создания экземпляров");
    }

    /**
     * Фильтрует список чисел, оставляя только четные.
     *
     * @param numbers Список целых чисел
     * @return Новый список, содержащий только четные числа из исходного списка
     */
    public static List<Integer> filterEvenNumbers(final List<Integer> numbers) {
        final List<Integer> result = new ArrayList<>();
        if (numbers == null) {
            return result;
        }

        for (final Integer num : numbers) {
            if (num != null && num % 2 == 0) {
                result.add(num);
            }
        }
        return result;
    }
}
