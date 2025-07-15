package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberFilterTest {

    @Test
    void shouldReturnOnlyEvenNumbersFromMixedList() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result)
                .hasSize(3)
                .containsExactly(2, 4, 6);
    }

    @Test
    void shouldReturnEmptyListWhenSourceContainsOnlyOddNumbers() {
        List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllNumbersWhenSourceContainsOnlyEvenNumbers() {
        List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result)
                .hasSize(5)
                .containsExactly(2, 4, 6, 8, 10);
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsEmpty() {
        List<Integer> numbers = Collections.emptyList();
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenSourceIsNull() {
        List<Integer> result = NumberFilter.filterEvenNumbers(null);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullElementsWhenFilteringList() {
        List<Integer> numbers = Arrays.asList(null, 2, null, 3, 4, null, 5);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result)
                .hasSize(2)
                .containsExactly(2, 4);
    }

    @Test
    void shouldHandleCustomScenarioForFilterEvenNumbers() {
        List<Integer> numbers = Arrays.asList(0, -2, -3, -4, null, 7, 8);
        List<Integer> result = NumberFilter.filterEvenNumbers(numbers);
        assertThat(result)
                .hasSize(4)
                .containsExactly(0, -2, -4, 8);
    }
}
