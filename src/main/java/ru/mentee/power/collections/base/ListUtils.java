package ru.mentee.power.collections.base;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListUtils {
    /**
     * Объединяет два списка строк в один, удаляя дубликаты.
     *
     * @param list1 Первый список строк
     * @param list2 Второй список строк
     * @return Новый список, содержащий все уникальные элементы из обоих списков
     */
    public static List<String> mergeLists(List<String> list1, List<String> list2) {
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