package ru.mentee.power.loop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class ForEachListExampleTest {

    @Test
    void listShouldContainCorrectColors() {
        List<String> colors = List.of("Красный", "Зеленый", "Синий");

        assertEquals(3, colors.size(), "Размер списка должен быть 3");
        assertTrue(colors.contains("Красный"), "Список должен содержать 'Красный'");
        assertTrue(colors.contains("Зеленый"), "Список должен содержать 'Зеленый'");
        assertTrue(colors.contains("Синий"), "Список должен содержать 'Синий'");
    }

    @Test
    void listShouldBeImmutable() {
        List<String> colors = List.of("Красный", "Зеленый", "Синий");

        assertThrows(UnsupportedOperationException.class, () -> {
            colors.add("Желтый");
        }, "Список должен быть иммутабельным и выбрасывать UnsupportedOperationException при попытке изменить");
    }
}
