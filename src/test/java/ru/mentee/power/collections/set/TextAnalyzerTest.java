package ru.mentee.power.collections.set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TextAnalyzerTest {

    @Test
    @DisplayName("Метод findUniqueWords должен находить все уникальные слова в тексте")
    void shouldFindUniqueWordsInText() {
        String text = "Привет, мир! Привет всем в этом мире!";
        Set<String> expected = new HashSet<>(Arrays.asList("привет", "мир", "всем", "в", "этом", "мире"));

        Set<String> result = TextAnalyzer.findUniqueWords(text);

        assertThat(result)
                .isNotNull()
                .hasSize(6)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findUniqueWords должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullTextInFindUniqueWords() {
        assertThatThrownBy(() -> TextAnalyzer.findUniqueWords(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findUniqueWords должен вернуть пустое множество для пустого текста")
    void shouldReturnEmptySetForEmptyTextInFindUniqueWords() {
        Set<String> result = TextAnalyzer.findUniqueWords("");
        assertThat(result).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Метод findCommonWords должен находить общие слова в двух текстах (операция пересечения)")
    void shouldFindCommonWordsInTexts() {
        String text1 = "яблоко груша персик виноград";
        String text2 = "виноград лимон апельсин яблоко";
        Set<String> expected = new HashSet<>(Arrays.asList("яблоко", "виноград"));

        Set<String> result = TextAnalyzer.findCommonWords(text1, text2);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findCommonWords должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFindCommonWords() {
        assertThatThrownBy(() -> TextAnalyzer.findCommonWords(null, ""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> TextAnalyzer.findCommonWords("", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findUniqueWordsInFirstText должен находить слова, уникальные для первого текста (операция разности)")
    void shouldFindUniqueWordsInFirstText() {
        String text1 = "apple banana orange";
        String text2 = "banana cherry kiwi";
        Set<String> expected = new HashSet<>(Arrays.asList("apple", "orange"));

        Set<String> result = TextAnalyzer.findUniqueWordsInFirstText(text1, text2);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findUniqueWordsInFirstText должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFindUniqueWordsInFirstText() {
        assertThatThrownBy(() -> TextAnalyzer.findUniqueWordsInFirstText(null, ""))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> TextAnalyzer.findUniqueWordsInFirstText("", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findTopNWords должен находить наиболее часто встречающиеся слова")
    void shouldFindTopNWords() {
        String text = "яблоко яблоко виноград виноград виноград апельсин апельсин апельсин апельсин";
        Set<String> expected = new HashSet<>(Arrays.asList("апельсин", "виноград", "яблоко"));

        Set<String> result = TextAnalyzer.findTopNWords(text, 3);

        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findTopNWords должен выбросить исключение при некорректных аргументах")
    void shouldThrowExceptionForInvalidArgumentsInFindTopNWords() {
        assertThatThrownBy(() -> TextAnalyzer.findTopNWords("", -1))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> TextAnalyzer.findTopNWords(null, 3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод findAnagrams должен находить группы анаграмм")
    void shouldFindAnagrams() {
        List<String> words = Arrays.asList("cat", "act", "dog", "god", "bird", "drib", "loop", "pool", "silent", "listen", "abc", "def");

        Set<Set<String>> expected = new HashSet<>();
        expected.add(new TreeSet<>(Arrays.asList("cat", "act")));
        expected.add(new TreeSet<>(Arrays.asList("dog", "god")));
        expected.add(new TreeSet<>(Arrays.asList("bird", "drib")));
        expected.add(new TreeSet<>(Arrays.asList("loop", "pool")));
        expected.add(new TreeSet<>(Arrays.asList("silent", "listen")));

        Set<Set<String>> actual = TextAnalyzer.findAnagrams(words);

        assertThat(actual)
                .isNotNull()
                .hasSize(expected.size())
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Метод findAnagrams должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInFindAnagrams() {
        assertThatThrownBy(() -> TextAnalyzer.findAnagrams(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод isSubset должен проверять, является ли одно множество подмножеством другого")
    void shouldCheckIfSetIsSubset() {
        Set<Integer> subset = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> superset = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

        boolean result1 = TextAnalyzer.isSubset(subset, superset);
        boolean result2 = TextAnalyzer.isSubset(superset, subset);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("Метод isSubset должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInIsSubset() {
        assertThatThrownBy(() -> TextAnalyzer.isSubset(null, new HashSet<>()))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> TextAnalyzer.isSubset(new HashSet<>(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод removeStopWords должен удалять стоп-слова из текста")
    void shouldRemoveStopWordsFromText() {
        String text = "Это простой пример простого теста.";
        Set<String> stopWords = new HashSet<>(Arrays.asList("это", "простой", "простого"));
        String expected = "пример теста";

        String result = TextAnalyzer.removeStopWords(text, stopWords).replaceAll("\\p{Punct}+", "");

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Метод removeStopWords должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInRemoveStopWords() {
        assertThatThrownBy(() -> TextAnalyzer.removeStopWords(null, new HashSet<>()))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> TextAnalyzer.removeStopWords("", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод compareSetPerformance должен сравнивать производительность разных типов множеств")
    void shouldCompareSetPerformance() {
        List<String> words = Arrays.asList("apple", "banana", "cherry", "grape", "kiwi");
        Map<String, Long> result = TextAnalyzer.compareSetPerformance(words);

        assertThat(result)
                .isNotNull()
                .hasSizeGreaterThanOrEqualTo(3) // Должны присутствовать замеры хотя бы для трех типов Set
                .allSatisfy((key, value) -> assertTrue(value >= 0)); // Время замеров должно быть неотрицательным
    }
}