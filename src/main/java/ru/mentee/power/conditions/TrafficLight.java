package ru.mentee.power.conditions;

import java.util.Scanner;

public class TrafficLight {

    /**
     * Возвращает рекомендацию для пешехода в зависимости от сигнала светофора.
     *
     * @param signal строковое представление сигнала ("Красный", "Желтый", "Зеленый")
     * @return Рекомендация для пешехода
     */
    public static String getRecommendation(String signal) {
        if (signal == null) {
            return "Некорректный сигнал!";
        }

        if (signal.equalsIgnoreCase("Красный")) {
            return "Стой на месте!";
        } else if (signal.equalsIgnoreCase("Желтый")) {
            return "Приготовься, но подожди!";
        } else if (signal.equalsIgnoreCase("Зеленый")) {
            return "Можно переходить дорогу!";
        } else {
            return "Некорректный сигнал!";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Какой сейчас сигнал светофора (Красный, Желтый, Зеленый)?");
        System.out.print("Введите название сигнала: ");

        String signal = scanner.nextLine(); // Считываем строку
        String recommendation = getRecommendation(signal); // Вызываем наш метод
        System.out.println(recommendation);

        scanner.close();
    }
}
