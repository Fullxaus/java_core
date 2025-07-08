package ru.mentee.power.loop;

import java.util.List;

public class ContinueExample {
    public int sumOfPositives(List<Integer> data) {
        if (data == null) {
            throw new NullPointerException("Список null");
        }

        int sumOfPositives = 0;
        for (int value : data) {
            if (value <= 0) {
                continue;
            }
            sumOfPositives += value;
        }
        return sumOfPositives;
    }
    public static void main(String[] args) {
        List<Integer> data = List.of(5, -2, 10, 0, -8, 3);
        int sumOfPositives = 0;

        System.out.println("Суммируем только положительные числа...");
        for (int value : data) {
            if (value <= 0) {
                System.out.println("Пропускаем: " + value);
                continue; // Переходим к следующему числу, если текущее не положительное
            }
            System.out.println("Добавляем: " + value);
            sumOfPositives += value;
        }

        System.out.println("Сумма положительных: " + sumOfPositives);
    }
}
