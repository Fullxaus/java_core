package ru.mentee.power.loop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WhileLoopExampleTest {

    @Test
    void testCountdownSequence() {
        int countdown = 3;
        int steps = 0;

        while (countdown > 0) {
            assertTrue(countdown > 0 && countdown <= 3, "countdown должен быть от 1 до 3");
            countdown--;
            steps++;
        }

        assertEquals(3, steps, "Цикл должен выполниться ровно 3 раза");
        assertEquals(0, countdown, "После цикла countdown должен быть 0");
    }
}
