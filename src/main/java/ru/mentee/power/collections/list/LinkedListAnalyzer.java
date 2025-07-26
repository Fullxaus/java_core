package ru.mentee.power.collections.list;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Класс для анализа и обработки связных списков (LinkedList).
 */
public final class LinkedListAnalyzer {

    private LinkedListAnalyzer() {
        // утилитарный класс не предназначен для инстанцирования
    }

    /**
     * Объединяет два списка, удаляя дубликаты.
     *
     * @param <T>   тип элементов списка
     * @param list1 первый список (не {@code null})
     * @param list2 второй список (не {@code null})
     * @return новый {@link LinkedList}, содержащий все уникальные элементы из обоих списков
     * @throws IllegalArgumentException если {@code list1} или {@code list2} равны {@code null}
     */
    public static <T> List<T> mergeLists(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) {
            throw new IllegalArgumentException("Оба входных списка должны быть не null");
        }

        LinkedList<T> result = new LinkedList<>();
        Set<T> seen = new HashSet<>();

        // Добавляем из первого списка
        for (T elem : list1) {
            if (seen.add(elem)) {
                result.addLast(elem);
            }
        }

        // Добавляем из второго списка
        for (T elem : list2) {
            if (seen.add(elem)) {
                result.addLast(elem);
            }
        }

        return result;
    }

    /**
     * Переворачивает порядок элементов списка.
     *
     * @param <T>  тип элементов списка
     * @param list список для обращения (не {@code null})
     * @return тот же список ({@link LinkedList}) с обратным порядком элементов
     * @throws IllegalArgumentException если {@code list} равен {@code null}
     */
    public static <T> List<T> reverse(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        LinkedList<T> ll =
                list instanceof LinkedList
                        ? (LinkedList<T>) list
                        : new LinkedList<>(list);

        LinkedList<T> reversed = new LinkedList<>();
        for (T elem : ll) {
            reversed.addFirst(elem);
        }

        ll.clear();
        ll.addAll(reversed);
        return ll;
    }

    /**
     * Удаляет из списка все элементы, которые встречаются более одного раза, оставляя только их
     * первое вхождение.
     *
     * @param <T>  тип элементов списка
     * @param list список для обработки (не {@code null})
     * @return тот же список ({@link LinkedList}) с удалёнными дубликатами
     * @throws IllegalArgumentException если {@code list} равен {@code null}
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        LinkedList<T> ll =
                list instanceof LinkedList
                        ? (LinkedList<T>) list
                        : new LinkedList<>(list);

        Set<T> seen = new HashSet<>();
        Iterator<T> iterator = ll.iterator();
        while (iterator.hasNext()) {
            T elem = iterator.next();
            if (!seen.add(elem)) {
                iterator.remove();
            }
        }

        return ll;
    }

    /**
     * Реализует циклический сдвиг элементов списка на указанное количество позиций.
     *
     * @param <T>       тип элементов списка
     * @param list      список для сдвига (не {@code null})
     * @param positions количество позиций для сдвига (положительное — вправо, отрицательное —
     *                  влево)
     * @return тот же список ({@link LinkedList}) с циклически сдвинутыми элементами
     * @throws IllegalArgumentException если {@code list} равен {@code null}
     */
    public static <T> List<T> rotate(List<T> list, int positions) {
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }

        LinkedList<T> ll =
                list instanceof LinkedList
                        ? (LinkedList<T>) list
                        : new LinkedList<>(list);

        int size = ll.size();
        if (size == 0) {
            return ll;
        }

        int shift = positions % size;
        if (shift < 0) {
            shift += size;
        }
        if (shift == 0) {
            return ll;
        }

        for (int i = 0; i < shift; i++) {
            T tail = ll.removeLast();
            ll.addFirst(tail);
        }

        return ll;
    }
}