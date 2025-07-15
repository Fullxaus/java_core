package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionAnalyzerTest {

    @Test
    void shouldReturnStringsLongerThanMinLength() {
        // Arrange
        Collection<String> strings = Arrays.asList("a", "abc", "abcde", "xy");
        int minLength = 2;

        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

        // Assert
        assertThat(result)
                .hasSize(2)
                .containsExactly("abc", "abcde");
    }

    @Test
    void shouldReturnEmptyListWhenCollectionIsNull() {
        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(null, 3);
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenCollectionIsEmpty() {
        // Arrange
        Collection<String> strings = new ArrayList<>();
        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, 3);
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void shouldIgnoreNullAndEmptyStringsWhenFindingLongStrings() {
        // Arrange
        Collection<String> strings = Arrays.asList(null, "", "a", "abcd", "reallyLongString");
        int minLength = 3;
        // Act
        List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);
        // Assert
        assertThat(result).containsExactly("abcd", "reallyLongString");
    }

    @Test
    void shouldCalculateCorrectDigitSumForPositiveNumber() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(123);
        // Assert
        assertThat(result).isEqualTo(6); // 1 + 2 + 3 = 6
    }

    @Test
    void shouldCalculateCorrectDigitSumForNegativeNumber() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(-456);
        // Assert
        assertThat(result).isEqualTo(15); // 4 + 5 + 6 = 15
    }

    @Test
    void shouldReturnZeroAsDigitSumForZero() {
        // Act
        int result = CollectionAnalyzer.calculateDigitSum(0);
        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldCountNumbersWithDigitSumGreaterThanThreshold() {
        // Arrange
        Collection<Integer> numbers = Arrays.asList(12, 99, -45, 0, 7);
        int threshold = 6;
        // Act
        int count = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers, threshold);
        // Assert
        // Суммы цифр: 12 -> 3, 99 -> 18, -45 -> 9, 0 -> 0, 7 -> 7
        // Числа с суммой цифр > 6: 99, -45, 7 -> 3 элемента
        assertThat(count).isEqualTo(3);
    }

    @Test
    void shouldReturnZeroWhenCountingWithNullCollection() {
        // Act
        int count = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(null, 5);
        // Assert
        assertThat(count).isZero();
    }

    @Test
    void shouldReturnZeroWhenCountingWithEmptyCollection() {
        // Arrange
        Collection<Integer> numbers = new ArrayList<>();
        // Act
        int count = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers, 5);
        // Assert
        assertThat(count).isZero();
    }

    @Test
    void shouldHandleCustomScenarioForDigitSumCount() {
        // Arrange
        Collection<Integer> numbers = Arrays.asList(-101, 202, 303, 404, null);
        int threshold = 4;
        // Act
        int count = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers, threshold);
        // Суммы цифр: |-101|=1+0+1=2, 202=2+0+2=4, 303=3+0+3=6, 404=4+0+4=8
        // Числа с суммой >4: 303, 404 => 2 элемента
        // Assert
               assertThat(count).isEqualTo(2);
           }
        }
