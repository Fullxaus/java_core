package ru.mentee.power.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты для AdvancedCalculator")
public class AdvancedCalculatorTest {

    private AdvancedCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new AdvancedCalculator();
    }

    @Test
    @DisplayName("Суммирование обычных чисел")
    void shouldSumNormalNumbers() {
        List<Integer> numbers = List.of(1, 2, 3);
        int expectedSum = 6;

        int actualSum = calculator.sumIgnoringOver1000(numbers);

        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Игнорирование чисел > 1000")
    void shouldIgnoreNumbersGreaterThan1000() {
        List<Integer> numbers = List.of(2, 1001, 5, 2000);
        int expectedSum = 7; // 2 + 5

        int actualSum = calculator.sumIgnoringOver1000(numbers);

        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Обработка пустого списка")
    void shouldReturnZeroForEmptyList() {
        List<Integer> numbers = Collections.emptyList();
        int expectedSum = 0;

        int actualSum = calculator.sumIgnoringOver1000(numbers);

        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Граничный случай: Обработка null списка")
    void shouldReturnZeroForNullList() {
        List<Integer> numbers = null;
        int expectedSum = 0;

        int actualSum = calculator.sumIgnoringOver1000(numbers);

        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Граничный случай: Число ровно 1000")
    void shouldIncludeNumberExactly1000() {
        List<Integer> numbers = List.of(5, 1000, 10);
        int expectedSum = 1015; // 5 + 1000 + 10

        int actualSum = calculator.sumIgnoringOver1000(numbers);

        assertThat(actualSum).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Граничный случай: Список содержит null элементы — бросаем NPE")
    void shouldThrowNpeWhenListContainsNullElements() {
        // Arrange
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(null);
        numbers.add(20);

        // Act & Assert
        assertThatThrownBy(() -> calculator.sumIgnoringOver1000(numbers))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("intValue");
    }

    @Test
    @DisplayName("Тест с отрицательными числами")
    void shouldHandleNegativeNumbers() {
        List<Integer> numbers = List.of(-5, 10, -2, 1005);
        int expectedSum = 3; // -5 + 10 + (-2) = 3, 1005 игнорируется

        int actualSum = calculator.sumIgnoringOver1000(numbers);

        assertThat(actualSum).isEqualTo(expectedSum);
    }
}