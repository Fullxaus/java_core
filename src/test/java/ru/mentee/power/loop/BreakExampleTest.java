package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BreakExampleTest {

    @Test
    void testFirstNegativeNumber_Found() {
        // Arrange
        int[] numbers = {10, 5, 0, -3, 8, -1};
        BreakExample example = new BreakExample();

        // Act
        int firstNegative = example.findFirstNegative(numbers);

        // Assert
        assertEquals(-3, firstNegative);
    }

    @Test
    void testFirstNegativeNumber_NotFound() {
        // Arrange
        int[] numbers = {10, 5, 0, 8, 1};
        BreakExample example = new BreakExample();

        // Act
        int firstNegativeIndex = findFirstNegativeIndex(numbers);

        // Assert
        assertEquals(-1, firstNegativeIndex); // -1 обозначает, что не найдено
    }

    @Test
    void testFirstNegativeNumber_NullArray() {
        // Arrange
        int[] numbers = null;
        BreakExample example = new BreakExample();

        // Act and Assert
        assertThrows(NullPointerException.class, () -> findFirstNegativeIndex(numbers));
    }

    private int findFirstNegativeIndex(int[] numbers) {
        if (numbers == null) {
            throw new NullPointerException("Массив null");
        }

        int firstNegative = 0;
        for (int number : numbers) {
            if (number < 0) {
                firstNegative = number;
                return numbers.length;
            }
        }
        return -1; // не нашли
    }
}