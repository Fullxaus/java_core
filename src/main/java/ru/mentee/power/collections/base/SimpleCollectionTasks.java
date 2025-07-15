package ru.mentee.power.collections.base;

import java.util.List;

/**
 * Класс с простыми задачами по работе с коллекциями.
 */
public class SimpleCollectionTasks {

    /**
     * Подсчитывает количество строк в списке, начинающихся с заданной буквы
     * (без учета регистра).
     *
     * @param strings Список строк.
     * @param startChar Начальная буква.
     * @return Количество строк, начинающихся с startChar.
     *         Возвращает 0, если список равен null или пуст.
     */
    public static int countStringsStartingWith(List<String> strings, char startChar) {
        if (strings == null || strings.isEmpty()) {
            return 0;
        }

        int count = 0;
        char lowerStartChar = Character.toLowerCase(startChar);

        for (String str : strings) {
            if (str != null && !str.isEmpty()) {
                if (Character.toLowerCase(str.charAt(0)) == lowerStartChar) {
                    count++;
                }
            }
        }

        return count;
    }
}
