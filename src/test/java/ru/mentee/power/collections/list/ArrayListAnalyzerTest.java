package ru.mentee.power.collections.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArrayListAnalyzerTest {

    @Test
    @DisplayName("Метод filterByPrefix должен корректно фильтровать строки по префиксу")
    void shouldFilterByPrefixCorrectly() {
        List<String> input = new ArrayList<>(Arrays.asList("apple", "banana", "apricot", "orange", "app"));
        List<String> expected = Arrays.asList("apple", "apricot", "app");

        List<String> result = ArrayListAnalyzer.filterByPrefix(input, "ap");

        assertThat(result)
                .isNotNull()
                .hasSize(3)
                .containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод filterByPrefix должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInFilterByPrefix() {
        assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(null, "test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("strings");

        assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(Arrays.asList("test"), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("prefix");
    }

    @Test
    @DisplayName("Метод filterByPrefix должен вернуть пустой список, если нет совпадений")
    void shouldReturnEmptyListWhenNoMatchesInFilterByPrefix() {
        List<String> input = Arrays.asList("dog", "cat", "bird");
        List<String> result = ArrayListAnalyzer.filterByPrefix(input, "z");
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("Метод findMax должен корректно находить максимальный элемент")
    void shouldFindMaxCorrectly() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 8, 1, 9, 3));

        Integer result = ArrayListAnalyzer.findMax(numbers);

        assertEquals(9, result);
    }

    @Test
    @DisplayName("Метод findMax должен выбросить исключение для пустого списка или null")
    void shouldThrowExceptionForEmptyOrNullListInFindMax() {
        assertThatThrownBy(() -> ArrayListAnalyzer.findMax(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("numbers");

        assertThatThrownBy(() -> ArrayListAnalyzer.findMax(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("пустым");
    }

    @Test
    @DisplayName("Метод partition должен корректно разбивать список на части")
    void shouldPartitionListCorrectly() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<List<Integer>> result = ArrayListAnalyzer.partition(input, 3);

        // Ожидаем 3 части: [1,2,3], [4,5,6], [7]
        assertThat(result)
                .hasSize(3)
                .containsExactly(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6),
                        Collections.singletonList(7)
                );
    }

    @Test
    @DisplayName("Метод partition должен выбросить исключение при некорректных аргументах")
    void shouldThrowExceptionForInvalidArgumentsInPartition() {
        // Нулевой список
        assertThatThrownBy(() -> ArrayListAnalyzer.partition(null, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("list");

        // Нулевой или отрицательный размер части
        assertThatThrownBy(() -> ArrayListAnalyzer.partition(Arrays.asList(1, 2, 3), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("partSize");

        assertThatThrownBy(() -> ArrayListAnalyzer.partition(Arrays.asList(1, 2, 3), -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("partSize");
    }

    @Test
    @DisplayName("Метод isPalindrome должен корректно определять палиндромы")
    void shouldIdentifyPalindromesCorrectly() {
        // Чётный размер
        List<Integer> evenPal = Arrays.asList(1, 2, 3, 3, 2, 1);
        assertTrue(ArrayListAnalyzer.isPalindrome(evenPal));

        // Нечётный размер
        List<String> oddPal = Arrays.asList("a", "b", "c", "b", "a");
        assertTrue(ArrayListAnalyzer.isPalindrome(oddPal));

        // Не палиндром
        List<Integer> notPal = Arrays.asList(1, 2, 3, 4);
        assertFalse(ArrayListAnalyzer.isPalindrome(notPal));

        // С null-элементами
        List<String> withNull = Arrays.asList("x", null, "x");
        assertTrue(ArrayListAnalyzer.isPalindrome(withNull));

        List<String> withNullFalse = Arrays.asList("x", null, "y");
        assertFalse(ArrayListAnalyzer.isPalindrome(withNullFalse));
    }

    @Test
    @DisplayName("Метод isPalindrome должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInIsPalindrome() {
        assertThatThrownBy(() -> ArrayListAnalyzer.isPalindrome(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("list");
    }
}
