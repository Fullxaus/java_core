package ru.mentee.power.conditions;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для расчёта экологического рейтинга автомобиля
 * в зависимости от типа топлива, объёма двигателя,
 * расхода топлива, года выпуска и соответствия стандарту Евро.
 */
public class CarEcoRating {

    private static final int ERROR_CODE = -1;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 100;
    private static final int EURO_STANDARD_YEAR_THRESHOLD = 2020;

    private static final int BASE_RATING_ELECTRIC = 90;
    private static final int BASE_RATING_HYBRID = 70;
    private static final int BASE_RATING_DIESEL = 40;
    private static final int BASE_RATING_PETROL = 30;

    private static final List<String> VALID_FUEL_TYPES =
            Arrays.asList("Бензин", "Дизель", "Гибрид", "Электро");

    /**
     * Рассчитывает экологический рейтинг автомобиля.
     *
     * @param fuelType          тип топлива
     * @param engineVolume      объём двигателя в литрах
     * @param fuelConsumption   расход топлива (л/100 км или кВт·ч/100 км)
     * @param yearOfManufacture год выпуска
     * @param isEuroCompliant    соответствует ли стандарту Евро-6
     * @return рейтинг от 1 до 100 или ERROR_CODE при ошибке входных данных
     */
    public int calculateEcoRating(String fuelType,
                                  double engineVolume,
                                  double fuelConsumption,
                                  int yearOfManufacture,
                                  boolean isEuroCompliant) {
        if (!validateInput(fuelType, engineVolume, fuelConsumption,
                yearOfManufacture)) {
            return ERROR_CODE;
        }

        int baseRating = getBaseFuelTypeRating(fuelType);
        if (baseRating == ERROR_CODE) {
            return ERROR_CODE;
        }

        int rating = applyRatingModifiers(baseRating,
                fuelType,
                engineVolume,
                fuelConsumption,
                yearOfManufacture,
                isEuroCompliant);
        return clampRating(rating);
    }

    /**
     * Проверяет корректность входных данных.
     *
     * @return true, если входные данные валидны
     */
    private boolean validateInput(String fuelType,
                                  double engineVolume,
                                  double fuelConsumption,
                                  int yearOfManufacture) {
        if (!VALID_FUEL_TYPES.contains(fuelType)) {
            return false;
        }
        if (engineVolume < 0 || fuelConsumption < 0) {
            return false;
        }
        if (yearOfManufacture > Year.now().getValue()) {
            return false;
        }
        if ("Электро".equals(fuelType) && engineVolume != 0) {
            return false;
        }
        return true;
    }

    /**
     * Возвращает базовый рейтинг для заданного типа топлива.
     */
    private int getBaseFuelTypeRating(String fuelType) {
        switch (fuelType) {
            case "Электро":
                return BASE_RATING_ELECTRIC;
            case "Гибрид":
                return BASE_RATING_HYBRID;
            case "Дизель":
                return BASE_RATING_DIESEL;
            case "Бензин":
                return BASE_RATING_PETROL;
            default:
                return ERROR_CODE;
        }
    }

    /**
     * Применяет модификаторы к базовому рейтингу.
     */
    private int applyRatingModifiers(int baseRating,
                                     String fuelType,
                                     double engineVolume,
                                     double fuelConsumption,
                                     int yearOfManufacture,
                                     boolean isEuroCompliant) {
        int rating = baseRating;

        if ("Бензин".equals(fuelType) || "Дизель".equals(fuelType)) {
            rating -= (int) (engineVolume * 5);
            rating -= (int) (fuelConsumption * 2);
        } else if ("Электро".equals(fuelType)) {
            rating -= (int) (fuelConsumption * 0.5);
        }

        // Учёт возраста автомобиля
        int age = Year.now().getValue() - yearOfManufacture;
        if (age > 0) {
            rating -= age;
        }

        // Бонус за Евро-6 для бензина и дизеля
        if (("Бензин".equals(fuelType) || "Дизель".equals(fuelType))
                && isEuroCompliant) {
            rating += 10;
        }

        // Бонус для гибридов с низким расходом
        if ("Гибрид".equals(fuelType) && fuelConsumption < 5) {
            rating += 15;
        }

        return rating;
    }

    /**
     * Ограничивает рейтинг в заданном интервале.
     */
    private int clampRating(int rating) {
        return Math.max(MIN_RATING, Math.min(MAX_RATING, rating));
    }

    /**
     * Точка входа для консольного запуска.
     */
    public static void main(String[] args) {
        CarEcoRating ecoRating = new CarEcoRating();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Тип топлива (Бензин, Дизель, Гибрид, Электро):");
        String type = scanner.next();

        System.out.println("Объём двигателя (л):");
        double volume = scanner.nextDouble();

        System.out.println("Расход топлива (л/100 км или кВт·ч/100 км):");
        double consumption = scanner.nextDouble();

        System.out.println("Год выпуска:");
        int year = scanner.nextInt();

        System.out.println("Соответствует стандарту Евро-6 (да/нет):");
        String euro = scanner.next();
        boolean isEuro = "да".equalsIgnoreCase(euro);

        int rating = ecoRating.calculateEcoRating(type,
                volume,
                consumption,
                year,
                isEuro);
        if (rating == ERROR_CODE) {
            System.out.println("Ошибка расчёта рейтинга");
        } else {
            System.out.println("Экологический рейтинг: " + rating);
        }

        scanner.close();
    }
}