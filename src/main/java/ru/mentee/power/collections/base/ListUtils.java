package ru.mentee.power.collections.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Утилитный класс для работы со списками строк.
 */
public class ListUtils {

    /**
     * Объединяет два списка строк в один, удаляя дубликаты.
     * Корректно обрабатывает {@code null}.
     *
     * @param list1 первый список строк
     * @param list2 второй список строк
     * @return новый список, содержащий все уникальные элементы из обоих списков
     */
    public static List<String> mergeLists(
            final List<String> list1,
            final List<String> list2) {
        Set<String> uniqueStrings = new HashSet<>();

        if (list1 != null) {
            for (String s : list1) {
                uniqueStrings.add(s);
            }
        }

        if (list2 != null) {
            for (String s : list2) {
                uniqueStrings.add(s);
            }
        }

        return new ArrayList<>(uniqueStrings);
    }
}