package ru.mentee.power.conditions;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TrafficLight {

    // private constructor to prevent instantiation
    private TrafficLight() { /* no-op */ }

    // Логгер классa — статический, финальный, именованный по полному имени класса
    private static final Logger LOGGER =
            Logger.getLogger(TrafficLight.class.getName());

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
        String s = signal.trim();
        if (s.equalsIgnoreCase("Красный")) {
            return "Стой на месте!";
        } else if (s.equalsIgnoreCase("Желтый")) {
            return "Приготовься, но подожди!";
        } else if (s.equalsIgnoreCase("Зеленый")) {
            return "Можно переходить дорогу!";
        } else {
            return "Некорректный сигнал!";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Какой сейчас сигнал светофора (Красный, Желтый, Зеленый)?");
        LOGGER.info("Введите название сигнала:");

        String signal = scanner.nextLine();
        String recommendation = getRecommendation(signal);

        // В зависимости от характера сообщения, можно выбрать разный уровень
        if (recommendation.startsWith("Некоррект")) {
            LOGGER.log(Level.WARNING, recommendation);
        } else {
            LOGGER.info(recommendation);
        }

        scanner.close();
    }
}
