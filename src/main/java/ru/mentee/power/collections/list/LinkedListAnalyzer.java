package ru.mentee.power.collections.list;

import java.util.*;

/**
 * Класс для анализа и обработки связных списков (LinkedList)
 */
public class LinkedListAnalyzer {

    /**
     * Объединяет два списка, удаляя дубликаты
     *
     * @param list1 первый список
     * @param list2 второй список
     * @return новый LinkedList, содержащий все уникальные элементы из обоих списков
     * @throws IllegalArgumentException если list1 или list2 равны null
     */
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Оба входных списка должны быть не null");
        }
        // Используем LinkedList для эффективных вставок
        LinkedList<T> result = new LinkedList<>();
        Set<T> seen = new HashSet<>();

        // Обходим первый список через итератор
        Iterator<T> it1 = list1.iterator();
        while (it1.hasNext()) {
            T elem = it1.next();
            if (seen.add(elem)) {
                result.addLast(elem);
            }
        }
        // Обходим второй список через итератор
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            T elem = it2.next();
            if (seen.add(elem)) {
                result.addLast(elem);
            }
        }
        return result;
    }

    /**
     * Переворачивает список (изменяет порядок элементов на обратный)
     *
     * @param list список для обращения
     * @return тот же список (LinkedList) с обратным порядком элементов
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> reverse(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }
        // Для эффективных операций работаем с LinkedList
        LinkedList<T> ll = (list instanceof LinkedList)
                ? (LinkedList<T>) list
                : new LinkedList<>(list);

        // Построим перевёрнутый список, используя addFirst()
        LinkedList<T> reversed = new LinkedList<>();
        for (T elem : ll) {
            reversed.addFirst(elem);
        }
        // Заменяем содержимое оригинального списка
        ll.clear();
        ll.addAll(reversed);
        return ll;
    }

    /**
     * Удаляет из списка все элементы, которые встречаются более одного раза,
     * оставляя только их первое вхождение
     *
     * @param list список для обработки
     * @return тот же список (LinkedList) с удалёнными дубликатами
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }
        LinkedList<T> ll = (list instanceof LinkedList)
                ? (LinkedList<T>) list
                : new LinkedList<>(list);

        Set<T> seen = new HashSet<>();
        // Удаляем дубликаты за один проход через итератор
        Iterator<T> it = ll.iterator();
        while (it.hasNext()) {
            T elem = it.next();
            if (!seen.add(elem)) {
                it.remove();
            }
        }
        return ll;
    }

    /**
     * Реализует циклический сдвиг элементов списка на указанное количество позиций
     *
     * @param list      список для сдвига
     * @param positions количество позиций для сдвига
     *                  (положительное — вправо, отрицательное — влево)
     * @return тот же список (LinkedList) с циклически сдвинутыми элементами
     * @throws IllegalArgumentException если list равен null
     */
    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }
        LinkedList<T> ll = (list instanceof LinkedList)
                ? (LinkedList<T>) list
                : new LinkedList<>(list);

        int size = ll.size();
        if (size == 0 || positions % size == 0) {
            return ll;
        }
        // Нормализуем количество сдвигов вправо
        int shift = positions % size;
        if (shift < 0) {
            shift += size;
        }
        // Для каждого шага вправо: removeLast() + addFirst()
        for (int i = 0; i < shift; i++) {
            T tail = ll.removeLast();
            ll.addFirst(tail);
        }
        return ll;
    }
}
