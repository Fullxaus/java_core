package ru.mentee.power.loop;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeDrawer {

    private static final Logger logger = Logger.getLogger(ShapeDrawer.class.getName());

    /**
     * Проверяет, находится ли ячейка (row, col) на границе квадрата размера size.
     */
    private boolean isBorder(int row, int col, int size) {
        // единственный булев метод – нет длинных «||» в теле цикла
        return row == 0
                || row == size - 1
                || col == 0
                || col == size - 1;
    }

    /**
     * Метод рисует заполненный квадрат заданного размера
     */
    public String drawSquare(int size) {
        StringBuilder sb = new StringBuilder(size * (size + 1));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append('*');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Метод рисует пустой квадрат (только границы) заданного размера
     */
    public String drawEmptySquare(int size) {
        StringBuilder sb = new StringBuilder(size * (size + 1));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isBorder(i, j, size)) {
                    sb.append('*');
                } else {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Метод рисует прямоугольный треугольник заданной высоты
     */
    public String drawTriangle(int height) {
        StringBuilder sb = new StringBuilder(height * (height + 1) / 2 + height);
        for (int i = 1; i <= height; i++) {
            for (int j = 0; j < i; j++) {
                sb.append('*');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Метод рисует ромб заданного размера (должен быть положительным и нечётным)
     */
    public String drawRhombus(int size) {
        if (size <= 0 || (size & 1) == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(size * (size + 1));
        int mid = size / 2;

        for (int i = 0; i < size; i++) {
            int starsCount = (i <= mid) ? (2 * i + 1) : (2 * (size - i - 1) + 1);
            int spacesCount = (size - starsCount) / 2;

            for (int k = 0; k < spacesCount; k++) {
                sb.append(' ');
            }
            for (int k = 0; k < starsCount; k++) {
                sb.append('*');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Логирует фигуру через java.util.logging, без ненужного конструирования строк,
     * если уровень INFO не включён.
     */
    public void printShape(String header, String shape) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(header + "\n" + shape);
        }
    }

    public static void main(String[] args) {
        ShapeDrawer drawer = new ShapeDrawer();
        drawer.printShape("Квадрат 5x5:", drawer.drawSquare(5));
        drawer.printShape("Пустой квадрат 5x5:", drawer.drawEmptySquare(5));
        drawer.printShape("Треугольник высотой 5:", drawer.drawTriangle(5));
        drawer.printShape("Ромб размером 5:", drawer.drawRhombus(5));
    }
}
