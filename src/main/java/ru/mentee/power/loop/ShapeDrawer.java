package ru.mentee.power.loop;

public class ShapeDrawer {

    /**
     * Метод рисует заполненный квадрат заданного размера
     */
    public String drawSquare(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append("*");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Метод рисует пустой квадрат (только границы) заданного размера
     */
    public String drawEmptySquare(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Границы квадрата
                if (i == 0 || i == size - 1 || j == 0 || j == size -1) {
                    sb.append("*");
                } else {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Метод рисует прямоугольный треугольник заданной высоты
     */
    public String drawTriangle(int height) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= height; i++) {
            // вывод i звездочек в строку
            for (int j = 0; j < i; j++) {
                sb.append("*");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Метод рисует ромб заданного размера (нечетное число)
     */
    public String drawRhombus(int size) {
        if (size <= 0 || size % 2 == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int mid = size / 2;

        for (int i = 0; i < size; i++) {
            int starsCount = i <= mid ? i * 2 + 1 : (size - i - 1) * 2 + 1;
            int spacesCount = (size - starsCount) / 2;

            // пробелы перед звёздочками
            for (int j = 0; j < spacesCount; j++) {
                sb.append(" ");
            }
            // звёздочки
            for (int j = 0; j < starsCount; j++) {
                sb.append("*");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Метод выводит фигуру в консоль
     */
    public void printShape(String shape) {
        System.out.println(shape);
    }

    /**
     * Главный метод для демонстрации работы программы
     */
    public static void main(String[] args) {
        ShapeDrawer drawer = new ShapeDrawer();

        System.out.println("Квадрат 5x5:");
        drawer.printShape(drawer.drawSquare(5));

        System.out.println("\nПустой квадрат 5x5:");
        drawer.printShape(drawer.drawEmptySquare(5));

        System.out.println("\nТреугольник высотой 5:");
        drawer.printShape(drawer.drawTriangle(5));

        System.out.println("\nРомб размером 5:");
        drawer.printShape(drawer.drawRhombus(5));
    }
}