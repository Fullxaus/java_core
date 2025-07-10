package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShapeDrawerTest {

    private final ShapeDrawer drawer = new ShapeDrawer();

    @Test
    void testDrawSquare() {
        // Подготовка ожидаемого результата для квадрата 3x3
        String expected = "***\n***\n***";

        // Вызов тестируемого метода
        String result = drawer.drawSquare(3).trim();

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawEmptySquare() {
        // Подготовка ожидаемого результата для пустого квадрата 3x3
        String expected = "***\n* *\n***";

        // Вызов тестируемого метода
        String result = drawer.drawEmptySquare(3).trim();

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawTriangle() {
        // Подготовка ожидаемого результата для треугольника высотой 3
        String expected = "*\n**\n***";

        // Вызов тестируемого метода
        String result = drawer.drawTriangle(3).trim();

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDrawRhombus() {
        // Подготовка ожидаемого результата для ромба размером 5
        String expected = """
                  *  
                 ***
                *****
                 ***
                  *  
                """;

        // Вызов тестируемого метода
        String result = drawer.drawRhombus(5);

        // Проверка результата
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testWithZeroOrNegativeSizeSquare() {
        // Проверка поведения при size <= 0 для метода drawSquare
        assertThat(drawer.drawSquare(0)).isEqualTo("");
        assertThat(drawer.drawSquare(-1)).isEqualTo("");
    }

    @Test
    void testWithZeroOrNegativeSizeEmptySquare() {
        // Проверка поведения при size <= 0 для метода drawEmptySquare
        assertThat(drawer.drawEmptySquare(0)).isEqualTo("");
        assertThat(drawer.drawEmptySquare(-5)).isEqualTo("");
    }

    @Test
    void testWithZeroOrNegativeSizeTriangle() {
        // Проверка поведения при size <= 0 для метода drawTriangle
        assertThat(drawer.drawTriangle(0)).isEqualTo("");
        assertThat(drawer.drawTriangle(-3)).isEqualTo("");
    }

    @Test
    void testWithZeroOrNegativeSizeRhombus() {
        // Проверка поведения при size <= 0 или четном размере для метода drawRhombus
        assertThat(drawer.drawRhombus(0)).isEqualTo("");
        assertThat(drawer.drawRhombus(-1)).isEqualTo("");
        assertThat(drawer.drawRhombus(4)).isEqualTo("");
        assertThat(drawer.drawRhombus(5)).isNotEmpty();
    }

    @Test
    void testWithLargeSizeSquare() {
        // Проверка работы метода drawSquare с большим размером
        String result = drawer.drawSquare(10);
        assertThat(result.split("\n").length).isEqualTo(10);
        assertThat(result).hasLineCount(10);
    }

    @Test
    void testWithLargeSizeEmptySquare() {
        // Проверка работы метода drawEmptySquare с большим размером
        String result = drawer.drawEmptySquare(10);
        assertThat(result.split("\n").length).isEqualTo(10);
        assertThat(result).hasLineCount(10);

    }

    @Test
    void testWithLargeSizeTriangle() {
        String[] lines = drawer.drawTriangle(10).split("\n");
        assertThat(lines.length).isEqualTo(10);
        for (String line : lines) {
            assertThat(line.length()).isLessThanOrEqualTo(line.length());
        }
    }

    @Test
    void testWithLargeSizeRhombus() {
        // Проверка работы метода drawRhombus с большим размером
        String[] lines = drawer.drawRhombus(21).split("\n");
        assertThat(lines.length).isEqualTo(21);
        int totalSize = 0;
        for (String line : lines) {
            totalSize += line.length();
        }
        assertThat(totalSize).isEqualTo(331);
    }
}
