package ru.mentee.power.loop;

import java.util.List;

public class ForEachListExample {
    public static void main(String[] args) {
        // Создаем иммутабельный список (начиная с Java 9)
        List<String> colors = List.of("Красный", "Зеленый", "Синий");

        System.out.println("Цвета:");
        for (String color : colors) {
            System.out.println(color);
        }
    }
}
