package ru.mentee.power.collections.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Утилитарный класс для анализа текста с использованием множеств.
 */
public final class TextAnalyzer {

    private TextAnalyzer() {
        // Утилитарный класс не должен инстанцироваться
    }

    /**
     * Находит все уникальные слова в тексте.
     * Слова разделяются пробелами и любыми не-алфавитно-цифровыми символами.
     *
     * @param text текст для анализа (не {@code null})
     * @return множество уникальных слов в нижнем регистре
     * @throws IllegalArgumentException если {@code text} равен {@code null}
     */
    public static Set<String> findUniqueWords(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text не может быть null");
        }
        String[] tokens = text.toLowerCase()
                .split("[^a-zа-яё0-9]+");
        Set<String> unique = new HashSet<>();
        for (String w : tokens) {
            if (!w.isEmpty()) {
                unique.add(w);
            }
        }
        return unique;
    }

    /**
     * Находит слова, которые встречаются в обоих текстах (пересечение множеств).
     *
     * @param text1 первый текст (не {@code null})
     * @param text2 второй текст (не {@code null})
     * @return множество общих слов в нижнем регистре
     * @throws IllegalArgumentException если один из параметров равен {@code null}
     */
    public static Set<String> findCommonWords(String text1, String text2) {
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException("Оба текста должны быть не null");
        }
        Set<String> s1 = findUniqueWords(text1);
        Set<String> s2 = findUniqueWords(text2);
        s1.retainAll(s2);
        return s1;
    }

    /**
     * Находит слова, которые есть в первом тексте и отсутствуют во втором (разность множеств).
     *
     * @param text1 первый текст (не {@code null})
     * @param text2 второй текст (не {@code null})
     * @return множество слов, уникальных для первого текста, в нижнем регистре
     * @throws IllegalArgumentException если один из параметров равен {@code null}
     */
    public static Set<String> findUniqueWordsInFirstText(String text1, String text2) {
        if (text1 == null || text2 == null) {
            throw new IllegalArgumentException("Оба текста должны быть не null");
        }
        Set<String> s1 = findUniqueWords(text1);
        Set<String> s2 = findUniqueWords(text2);
        s1.removeAll(s2);
        return s1;
    }

    /**
     * Находит топ-N наиболее часто встречающихся слов в тексте.
     * При равной частоте — лексикографический порядок.
     *
     * @param text текст для анализа (не {@code null})
     * @param n    количество слов для возврата (должно быть > 0)
     * @return упорядоченное по убыванию частоты множество из n слов
     * @throws IllegalArgumentException если {@code text} равен {@code null} или {@code n <= 0}
     */
    public static Set<String> findTopNWords(String text, int n) {
        if (text == null) {
            throw new IllegalArgumentException("text не может быть null");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("n должно быть > 0");
        }

        String[] tokens = text.toLowerCase().split("[^a-zа-яё0-9]+");
        Map<String, Integer> freq = new HashMap<>();
        for (String w : tokens) {
            if (w.isEmpty()) {
                continue;
            }
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(freq.entrySet());
        entries.sort((e1, e2) -> {
            int cmp = e2.getValue().compareTo(e1.getValue());
            if (cmp != 0) {
                return cmp;
            }
            return e1.getKey().compareTo(e2.getKey());
        });

        Set<String> result = new LinkedHashSet<>();
        for (Map.Entry<String, Integer> entry : entries) {
            if (result.size() >= n) {
                break;
            }
            result.add(entry.getKey());
        }
        return result;
    }

    /**
     * Находит все группы анаграмм в списке слов.
     * Анаграммы — слова, составленные из одних и тех же букв.
     *
     * @param words список слов для анализа (не {@code null})
     * @return множество множеств, содержащих группы анаграмм
     * @throws IllegalArgumentException если {@code words} равен {@code null}
     */
    public static Set<Set<String>> findAnagrams(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("words не может быть null");
        }

        Map<String, Set<String>> groups = new HashMap<>();
        for (String word : words) {
            if (word == null) {
                continue;
            }
            char[] chars = word.toLowerCase(Locale.ROOT).toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new TreeSet<>())
                    .add(word);
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
     * Проверяет, является ли {@code set1} подмножеством {@code set2}.
     *
     * @param <T>  тип элементов множества
     * @param set1 первое множество (не {@code null})
     * @param set2 второе множество (не {@code null})
     * @return {@code true}, если все элементы set1 содержатся в set2
     * @throws IllegalArgumentException если один из параметров равен {@code null}
     */
    public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            throw new IllegalArgumentException("Ни одно из множеств не должно быть null");
        }
        return set2.containsAll(set1);
    }

    /**
     * Удаляет из текста все стоп-слова.
     *
     * @param text      исходный текст (не {@code null})
     * @param stopWords множество стоп-слов (не {@code null})
     * @return текст без стоп-слов
     * @throws IllegalArgumentException если один из параметров равен {@code null}
     */
    public static String removeStopWords(String text, Set<String> stopWords) {
        if (text == null) {
            throw new IllegalArgumentException("text не может быть null");
        }
        if (stopWords == null) {
            throw new IllegalArgumentException("stopWords не может быть null");
        }

        StringBuilder sb = new StringBuilder();
        for (String token : text.split("\\s+")) {
            String word = token.replaceAll("[^\\p{L}\\p{N}]", "")
                    .toLowerCase(Locale.ROOT);
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
     * Сравнивает производительность операций {@code add}, {@code contains} и {@code remove}
     * для разных реализаций {@link Set}.
     *
     * @param words список слов для тестирования (не {@code null})
     * @return карта с результатами в наносекундах:
     *         ключ — "{@code ТипSet}:{@code операция}", значение — время выполнения
     * @throws IllegalArgumentException если {@code words} равен {@code null}
     */
    public static Map<String, Long> compareSetPerformance(List<String> words) {
        if (words == null) {
            throw new IllegalArgumentException("words не может быть null");
        }

        Map<String, Set<String>> implementations = Map.of(
                "HashSet", new HashSet<>(),
                "LinkedHashSet", new LinkedHashSet<>(),
                "TreeSet", new TreeSet<>()
        );
        Map<String, Long> results = new HashMap<>();

        for (Map.Entry<String, Set<String>> impl : implementations.entrySet()) {
            String name = impl.getKey();
            Set<String> set = impl.getValue();

            long start = System.nanoTime();
            for (String w : words) {
                set.add(w);
            }
            long tAdd = System.nanoTime() - start;

            start = System.nanoTime();
            for (String w : words) {
                set.contains(w);
            }
            long tContains = System.nanoTime() - start;

            start = System.nanoTime();
            for (String w : words) {
                set.remove(w);
            }
            long tRemove = System.nanoTime() - start;

            results.put(name + ":add", tAdd);
            results.put(name + ":contains", tContains);
            results.put(name + ":remove", tRemove);
        }

        return results;
    }
}