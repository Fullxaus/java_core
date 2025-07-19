package ru.mentee.power.collections.set;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для анализа текста с использованием множеств
 */
public class TextAnalyzer {

    /**
     * Находит все уникальные слова в тексте
     * Слова разделяются пробелами и знаками препинания
     *
     * @param text текст для анализа
     * @return множество уникальных слов в нижнем регистре
     * @throws IllegalArgumentException если text равен null
     */
    public static Set<String> findUniqueWords(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text не может быть null");
        }
        // Разбиваем по любым не-буквенным символам
        String[] tokens = text.toLowerCase().split("[^a-zа-яё0-9]+");
        Set<String> unique = new HashSet<>();
        for (String w : tokens) {
            if (!w.isEmpty()) {
                unique.add(w);
            }
        }
        return unique;
    }

    /**
     * Находит все слова, которые встречаются в обоих текстах
     * Операция пересечения множеств
     *
     * @param text1 первый текст
     * @param text2 второй текст
     * @return множество общих слов в нижнем регистре
     * @throws IllegalArgumentException если text1 или text2 равны null
     */
    public static Set<String> findCommonWords(String text1, String text2) {
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException("Оба текста должны быть не null");
        }
        Set<String> s1 = findUniqueWords(text1);
        Set<String> s2 = findUniqueWords(text2);
        // пересечение
        s1.retainAll(s2);
        return s1;
    }

    /**
     * Находит все слова, которые встречаются в первом тексте, но отсутствуют во втором
     * Операция разности множеств
     *
     * @param text1 первый текст
     * @param text2 второй текст
     * @return множество слов, уникальных для первого текста, в нижнем регистре
     * @throws IllegalArgumentException если text1 или text2 равны null
     */
    public static Set<String> findUniqueWordsInFirstText(String text1, String text2) {
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException("Оба текста должны быть не null");
        }
        Set<String> s1 = findUniqueWords(text1);
        Set<String> s2 = findUniqueWords(text2);
        // разность
        s1.removeAll(s2);
        return s1;
    }

    /**
     * Находит топ-N наиболее часто встречающихся слов в тексте
     *
     * @param text текст для анализа
     * @param n    количество слов для возврата
     * @return множество из n наиболее часто встречающихся слов (в порядке убывания частоты)
     * @throws IllegalArgumentException если text равен null или n <= 0
     */
    public static Set<String> findTopNWords(String text, int n) {
        if (text == null) {
            throw new IllegalArgumentException("text не может быть null");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("n должно быть > 0");
        }
        // подсчёт частот
        String[] tokens = text.toLowerCase().split("[^a-zа-яё0-9]+");
        Map<String, Integer> freq = new HashMap<>();
        for (String w : tokens) {
            if (w.isEmpty()) continue;
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        // сортируем по убыванию частоты, при равенстве — по лексике
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freq.entrySet());
        entries.sort((e1, e2) -> {
            int cmp = e2.getValue().compareTo(e1.getValue());
            if (cmp != 0) return cmp;
            return e1.getKey().compareTo(e2.getKey());
        });
        // формируем результат
        Set<String> result = new LinkedHashSet<>();
        for (int i = 0; i < entries.size() && result.size() < n; i++) {
            result.add(entries.get(i).getKey());
        }
        return result;
    }

    /**
     * Находит все анаграммы в списке слов
     * Анаграммы — слова, составленные из одних и тех же букв
     *
     * @param words список слов для проверки
     * @return множество множеств, где каждое внутреннее множество содержит группу анаграмм
     * @throws IllegalArgumentException если words равен null
     */
    // Изменённый метод findAnagrams
    public static Set<Set<String>> findAnagrams(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("words не может быть null");
        }

        Map<String, Set<String>> groups = new HashMap<>();

        for (String word : words) {
            if (word == null) continue;
            char[] chars = word.toLowerCase().toCharArray(); // Приводим слово к нижнему регистру
            Arrays.sort(chars);                              // Отсортировываем символы
            String normalizedKey = new String(chars);         // Создаем нормализованный ключ
            groups.computeIfAbsent(normalizedKey, k -> new TreeSet<>()).add(word);
        }

        Set<Set<String>> result = new HashSet<>();
        for (Set<String> group : groups.values()) {
            if (group.size() > 1) {
                result.add(group);
            }
        }
        return result;
    }

    /**
     * Проверяет, является ли первое множество подмножеством второго
     *
     * @param <T>  тип элементов множества
     * @param set1 первое множество
     * @param set2 второе множество
     * @return true, если все элементы set1 содержатся в set2
     * @throws IllegalArgumentException если set1 или set2 равны null
     */
    public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            throw new IllegalArgumentException("Ни одно из множеств не должно быть null");
        }
        return set2.containsAll(set1);
    }

    /**
     * Удаляет все стоп-слова из текста
     *
     * @param text      исходный текст
     * @param stopWords множество стоп-слов
     * @return текст без стоп-слов
     * @throws IllegalArgumentException если text или stopWords равны null
     */
    public static String removeStopWords(String text, Set<String> stopWords) {
        if (text == null) {
            throw new IllegalArgumentException("text не может быть null");
        }
        if (stopWords == null) {
            throw new IllegalArgumentException("stopWords не может быть null");
        }
        String[] tokens = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            String word = token.replaceAll("[^a-zA-Zа-яА-ЯёЁ0-9]", "").toLowerCase();
            if (word.isEmpty() || stopWords.contains(word)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Сравнивает производительность работы с разными типами множеств
     *
     * @param words список слов для тестирования
     * @return Map с результатами замеров времени (наносекунды) для операций add, contains, remove
     *         на разных типах Set
     * @throws IllegalArgumentException если words равен null
     */
    public static Map<String, Long> compareSetPerformance(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("words не может быть null");
        }

        // Будем тестировать на трёх реализациях
        Map<String, Set<String>> implementations = Map.of(
                "HashSet", new HashSet<>(),
                "LinkedHashSet", new LinkedHashSet<>(),
                "TreeSet", new TreeSet<>()
        );

        Map<String, Long> results = new LinkedHashMap<>();

        for (Map.Entry<String, Set<String>> entry : implementations.entrySet()) {
            String name = entry.getKey();
            Set<String> set = entry.getValue();

            // 1) add
            long t0 = System.nanoTime();
            for (String w : words) {
                set.add(w);
            }
            long tAdd = System.nanoTime() - t0;

            // 2) contains
            t0 = System.nanoTime();
            for (String w : words) {
                set.contains(w);
            }
            long tContains = System.nanoTime() - t0;

            // 3) remove
            t0 = System.nanoTime();
            for (String w : words) {
                set.remove(w);
            }
            long tRemove = System.nanoTime() - t0;

            results.put(name + ":add", tAdd);
            results.put(name + ":contains", tContains);
            results.put(name + ":remove", tRemove);
        }

        return results;
    }
}