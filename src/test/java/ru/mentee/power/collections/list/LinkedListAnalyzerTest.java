package ru.mentee.power.collections.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinkedListAnalyzerTest {

    @Test
    @DisplayName("Метод mergeLists должен корректно объединять списки без дубликатов")
    void shouldMergeListsWithoutDuplicates() {
        LinkedList<Integer> list1 = new LinkedList<>(Arrays.asList(1, 2, 3));
        LinkedList<Integer> list2 = new LinkedList<>(Arrays.asList(3, 4, 5));

        List<Integer> result = LinkedListAnalyzer.mergeLists(list1, list2);

        assertThat(result)
                .isNotNull()
                .hasSize(5)
                .containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    @DisplayName("Метод mergeLists должен выбросить исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInMergeLists() {
        // для удобства проверяем одинаковое сообщение в обоих случаях
        String expectedMessage = "Оба входных списка должны быть не null";

        assertThatThrownBy(() -> LinkedListAnalyzer.mergeLists(new LinkedList<>(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);

        assertThatThrownBy(() -> LinkedListAnalyzer.mergeLists(null, new LinkedList<>()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    @DisplayName("Метод reverse должен корректно обращать порядок элементов")
    void shouldReverseListCorrectly() {
        LinkedList<Integer> input = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);

        List<Integer> result = LinkedListAnalyzer.reverse(input);

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод reverse должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInReverse() {
        assertThatThrownBy(() -> LinkedListAnalyzer.reverse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
    }

    @Test
    @DisplayName("Метод removeDuplicates должен удалять дубликаты, сохраняя порядок")
    void shouldRemoveDuplicatesCorrectly() {
        LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "a", "c", "b", "d"));
        List<String> expected = Arrays.asList("a", "b", "c", "d");

        List<String> result = LinkedListAnalyzer.removeDuplicates(input);

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод removeDuplicates должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInRemoveDuplicates() {
        assertThatThrownBy(() -> LinkedListAnalyzer.removeDuplicates(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
    }

    @Test
    @DisplayName("Метод rotate должен корректно сдвигать элементы вправо")
    void shouldRotateElementsToTheRight() {
        LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e"));
        List<String> expected = Arrays.asList("c", "d", "e", "a", "b");

        List<String> result = LinkedListAnalyzer.rotate(input, 3);

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод rotate должен корректно сдвигать элементы влево")
    void shouldRotateElementsToTheLeft() {
        LinkedList<String> input = new LinkedList<>(Arrays.asList("a", "b", "c", "d", "e"));
        // сдвиг на -2 = влево на 2 позиции -> ["c","d","e","a","b"]
        List<String> expected = Arrays.asList("c", "d", "e", "a", "b");

        List<String> result = LinkedListAnalyzer.rotate(input, -2);

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод rotate должен выбросить исключение при null аргументе")
    void shouldThrowExceptionForNullArgumentInRotate() {
        assertThatThrownBy(() -> LinkedListAnalyzer.rotate(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
    }
}
