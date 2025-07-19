package ru.mentee.power.collections.list;

import java.util.*;

/**
 * Класс для анализа и обработки списков на основе ArrayList
 */
public class ArrayListAnalyzer {

    /**
     * Фильтрует список строк, оставляя только те, которые начинаются с указанного префикса
     *
     * @param strings список строк для фильтрации
     * @param prefix  префикс для фильтрации
     * @return новый список, содержащий только строки с указанным префиксом
     * @throws IllegalArgumentException если strings или prefix равны null
     */
    public static List<String> filterByPrefix(List<String> strings, String prefix) {
        if (strings == null) {
            throw new IllegalArgumentException("Список strings не может быть null");
        }
        if (prefix == null) {
            throw new IllegalArgumentException("Префикс prefix не может быть null");
        }

        List<String> result = new ArrayList<>(strings.size());
        //  цикл для  работы с ArrayList
        for (int i = 0, n = strings.size(); i < n; i++) {
            String s = strings.get(i);
            if (s != null && s.startsWith(prefix)) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Находит максимальный элемент в списке
     *
     * @param numbers список чисел
     * @return максимальное число из списка
     * @throws IllegalArgumentException если список пуст или равен null
     */
    public static Integer findMax(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Список numbers не может быть null");
        }
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Список numbers не может быть пустым");
        }

        // Начинаем с первого элемента
        Integer max = numbers.get(0);
        for (int i = 1, n = numbers.size(); i < n; i++) {
            Integer val = numbers.get(i);
            if (val != null && val.compareTo(max) > 0) {
                max = val;
            }
        }
        return max;
    }

    /**
     * Разбивает список на части указанного размера
     *
     * @param list     исходный список
     * @param partSize размер каждой части
     * @return список списков, где каждый внутренний список имеет размер не более partSize
     * @throws IllegalArgumentException если list равен null или partSize <= 0
     */
    public static <T> List<List<T>> partition(List<T> list, int partSize) {
        if (list == null) {
            throw new IllegalArgumentException("Список list не может быть null");
        }
        if (partSize <= 0) {
            throw new IllegalArgumentException("Размер части partSize должен быть больше 0");
        }

        List<List<T>> result = new ArrayList<>((list.size() + partSize - 1) / partSize);
        for (int i = 0, n = list.size(); i < n; i += partSize) {
            // элемент найден и мы возвращаем новый список, чтобы не потерять главный список
            int end = Math.min(n, i + partSize);
            result.add(new ArrayList<>(list.subList(i, end)));
        }
        return result;
    }

    /**
     * Проверяет, является ли список палиндромом
     * (читается одинаково в обоих направлениях)
     *
     * @param list список для проверки
     * @return true, если список является палиндромом, иначе false
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> boolean isPalindrome(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список list не может быть null");
        }

        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            T a = list.get(left);
            T b = list.get(right);
            if (a == null) {
                if (b != null) {
                    return false;
                }
            } else if (!a.equals(b)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}