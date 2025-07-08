package ru.mentee.power.loop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ForLoopExampleTest {

    @Test
    void arrayShouldContainCorrectFruits() {
        String[] fruits = {"Яблоко", "Банан", "Апельсин"};

        assertEquals(3, fruits.length, "Длина массива должна быть 3");
        assertEquals("Яблоко", fruits[0], "Первый элемент должен быть 'Яблоко'");
        assertEquals("Банан", fruits[1], "Второй элемент должен быть 'Банан'");
        assertEquals("Апельсин", fruits[2], "Третий элемент должен быть 'Апельсин'");
    }

    @Test
    void arrayShouldNotContainNulls() {
        String[] fruits = {"Яблоко", "Банан", "Апельсин"};

        for (String fruit : fruits) {
            assertNotNull(fruit, "Элемент массива не должен быть null");
        }
    }
}
