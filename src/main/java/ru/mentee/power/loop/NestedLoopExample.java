package ru.mentee.power.loop;

public class NestedLoopExample {
    public static void main(String[] args) {
        System.out.println("Пары координат:");
        // Внешний цикл по X
        for (int x = 1; x <= 2; x++) {
            // Внутренний цикл по Y
            for (int y = 1; y <= 3; y++) {
                System.out.println("X=" + x + ", Y=" + y);
            }
            System.out.println("--- Следующий X ---"); // Разделитель для наглядности
        }
    }
}
