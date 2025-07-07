package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContinueExampleTest {

    @Test
    void testSumOfPositives_Found() {
        // Arrange
        List<Integer> data = List.of(5, -2, 10, 0, -8, 3);
        ContinueExample example = new ContinueExample();

        // Act
        int sumOfPositives = example.sumOfPositives(data);

        // Assert
        assertEquals(18, sumOfPositives);
    }

    @Test
    void testSumOfPositives_NoPositives() {
        // Arrange
        List<Integer> data = List.of(-5, -2, -10, 0, -8, -3);
        ContinueExample example = new ContinueExample();

        // Act
        int sumOfPositives = example.sumOfPositives(data);

        // Assert
        assertEquals(0, sumOfPositives);
    }

    @Test
    void testSumOfPositives_EmptyList() {
        // Arrange
        List<Integer> data = List.of();
        ContinueExample example = new ContinueExample();

        // Act
        int sumOfPositives = example.sumOfPositives(data);

        // Assert
        assertEquals(0, sumOfPositives);
    }

    @Test
    void testSumOfPositives_NullList() {
        // Arrange
        List<Integer> data = null;
        ContinueExample example = new ContinueExample();

        // Act and Assert
        assertThrows(NullPointerException.class, () -> example.sumOfPositives(data));
    }
}
