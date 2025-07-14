package ru.mentee.power.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Утилитарный класс для работы со строками.
 */
public class StringUtils {

    /**
     * Подсчитывает количество вхождений символа в строке.
     *
     * @param str    Исходная строка
     * @param target Искомый символ
     * @return Количество вхождений символа
     */
    public static int countChars(String str, char target) {
        if (str == null) {
            return 0;
        }
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }

    /**
     * Обрезает строку до указанной максимальной длины.
     * Если строка длиннее maxLength, добавляет "..." в конце.
     *
     * @param str       Исходная строка
     * @param maxLength Максимальная длина
     * @return Обрезанная строка
     */
    public static String truncate(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (maxLength < 0) {
            // Необязательное поведение: если отрицательная длина — возвращаем пустую строку
            return "";
        }
        if (str.length() <= maxLength) {
            return str;
        }
        // Обрезаем до maxLength символов и добавляем "..."
        return str.substring(0, maxLength) + "...";
    }

    /**
     * Проверяет, является ли строка палиндромом.
     * Игнорирует регистр и не-буквенные символы.
     *
     * @param str Исходная строка
     * @return true, если строка является палиндромом, иначе false
     */
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        // Оставляем только буквы, приводим к одному регистру
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        String cleaned = sb.toString();
        // Сравниваем с перевёрнутой
        String reversed = sb.reverse().toString();
        return cleaned.equals(reversed);
    }

    /**
     * Заменяет все последовательности пробельных символов одним пробелом.
     * Удаляет пробелы в начале и конце строки.
     *
     * @param str Исходная строка
     * @return Нормализованная строка
     */
    public static String normalizeSpaces(String str) {
        if (str == null) {
            return "";
        }
        // Убираем ведущие и конечные пробелы, затем заменяем все виды пробельных символов
        // (пробел, табуляция, перевод строки и т.п.) на одиночный пробел
        return str.trim().replaceAll("\\s+", " ");
    }

    /**
     * Объединяет массив строк, используя указанный разделитель.
     *
     * @param strings   Массив строк
     * @param delimiter Разделитель
     * @return Объединенная строка
     */
    public static String join(String[] strings, String delimiter) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        if (delimiter == null) {
            delimiter = "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String element : strings) {
            if (element == null) {
                // Пропускаем null-элементы
                continue;
            }
            if (!first) {
                sb.append(delimiter);
            }
            sb.append(element);
            first = false;
        }
        return sb.toString();
    }


}