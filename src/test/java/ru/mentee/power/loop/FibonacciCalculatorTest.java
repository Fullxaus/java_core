package ru.mentee.power.loop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.time.Duration;

public class FibonacciCalculatorTest {

    private FibonacciCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new FibonacciCalculator();
    }

    @Test
    public void testFibonacciRecursive() {
        assertThat(calculator.fibonacciRecursive(0)).isEqualTo(0);
        assertThat(calculator.fibonacciRecursive(1)).isEqualTo(1);
        assertThat(calculator.fibonacciRecursive(10)).isEqualTo(55);
    }

    @Test
    public void testFibonacciIterative() {
        assertThat(calculator.fibonacciIterative(0)).isEqualTo(0);
        assertThat(calculator.fibonacciIterative(1)).isEqualTo(1);
        assertThat(calculator.fibonacciIterative(10)).isEqualTo(55);
    }

    @Test
    public void testFibonacciWithCache() {
        assertThat(calculator.fibonacciWithCache(0)).isEqualTo(0);
        assertThat(calculator.fibonacciWithCache(1)).isEqualTo(1);
        assertThat(calculator.fibonacciWithCache(10)).isEqualTo(55);
        // Проверка кеширования, повторный вызов для ускорения
        assertThat(calculator.fibonacciWithCache(10)).isEqualTo(55);
    }

    @Test
    public void testGenerateFibonacciSequence() {
        long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34};
        assertThat(calculator.generateFibonacciSequence(10)).containsExactly(expected);
    }

    @Test
    public void testIsFibonacciNumber() {
        assertThat(calculator.isFibonacciNumber(0)).isFalse();
        assertThat(calculator.isFibonacciNumber(1)).isTrue();
        assertThat(calculator.isFibonacciNumber(8)).isTrue();
        assertThat(calculator.isFibonacciNumber(10)).isFalse();
        assertThat(calculator.isFibonacciNumber(13)).isTrue();
        assertThat(calculator.isFibonacciNumber(15)).isFalse();
        assertThat(calculator.isFibonacciNumber(21)).isTrue();
        assertThat(calculator.isFibonacciNumber(-5)).isFalse();
    }

    @Test
    public void testPerformanceOfIterativeMethod() {
        // Проверка, что вычисление F(50) не занимает больше 1 секунды
        assertTimeout(Duration.ofSeconds(1), () -> {
            long result = calculator.fibonacciIterative(50);
            assertThat(result).isGreaterThan(0);
        });
    }

    @Test
    public void testFibonacciRecursiveVeryNegativeValue() {
        FibonacciCalculator calculator = new FibonacciCalculator();
        assertThrows(IllegalArgumentException.class, () -> calculator.fibonacciRecursive(-100));
    }
}