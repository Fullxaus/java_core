package ru.mentee.power.loop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NestedLoopExampleTest {

    @Test
    void testCoordinatesPairs() {
        int count = 0;
        for (int x = 1; x <= 2; x++) {
            for (int y = 1; y <= 3; y++) {
                assertTrue(x >= 1 && x <= 2, "x должен быть в диапазоне 1..2");
                assertTrue(y >= 1 && y <= 3, "y должен быть в диапазоне 1..3");
                count++;
            }
        }
        assertEquals(6, count, "Всего должно быть 6 пар координат");
    }

    @Test
    void testSeparatorsCount() {
        // В вашем коде на каждой итерации внешнего цикла выводится разделитель
        // Кол-во разделителей = кол-во итераций внешнего цикла = 2
        int separatorsCount = 2;
        assertEquals(2, separatorsCount, "Количество разделителей должно быть равно количеству внешних итераций");
    }
}
