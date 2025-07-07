package ru.mentee.power.conditions;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CarEcoRating {

    private static final int ERROR_CODE = -1;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 100;
    private static final int EURO_STANDARD_YEAR_THRESHOLD = 2020;

    private static final int BASE_RATING_ELECTRIC = 90;
    private static final int BASE_RATING_HYBRID = 70;
    private static final int BASE_RATING_DIESEL = 40;
    private static final int BASE_RATING_PETROL = 30;

    private static final List<String> VALID_FUEL_TYPES = Arrays.asList("Бензин", "Дизель", "Гибрид", "Электро");

    public int calculateEcoRating(String fuelType, double engineVolume, double fuelConsumption, int yearOfManufacture, boolean isEuroCompliant) {
        if (!validateInput(fuelType, engineVolume, fuelConsumption, yearOfManufacture)) {
            return ERROR_CODE;
        }

        int baseRating = getBaseFuelTypeRating(fuelType);
        if (baseRating == ERROR_CODE) {
            return ERROR_CODE;
        }

        int rating = applyRatingModifiers(baseRating, fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);
        return clampRating(rating);
    }

    private boolean validateInput(String fuelType, double engineVolume, double fuelConsumption, int yearOfManufacture) {
        if (!VALID_FUEL_TYPES.contains(fuelType)) {
            return false;
        }
        if (engineVolume < 0 || fuelConsumption < 0) {
            return false;
        }
        if (yearOfManufacture > Year.now().getValue()) {
            return false;
        }
        if (fuelType.equals("Электро") && engineVolume != 0) {
            return false;
        }
        return true;
    }

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

    private int applyRatingModifiers(int baseRating, String fuelType, double engineVolume, double fuelConsumption, int yearOfManufacture, boolean isEuroCompliant) {
        int rating = baseRating;

        if (fuelType.equals("Бензин") || fuelType.equals("Дизель")) {
            rating -= (int) (engineVolume * 5);
            rating -= (int) (fuelConsumption * 2);
        } else if (fuelType.equals("Электро")) {
            rating -= (int) (fuelConsumption * 0.5);
        }

        rating -= Math.max(0, Year.now().getValue() - yearOfManufacture);

        if (fuelType.equals("Бензин") || fuelType.equals("Дизель")) {
            if (isEuroCompliant) {
                rating += 10;
            }
        }

        if (fuelType.equals("Гибрид") && fuelConsumption < 5) {
            rating += 15;
        }

        return rating;
    }

    private int clampRating(int rating) {
        return Math.max(MIN_RATING, Math.min(MAX_RATING, rating));
    }

    public static void main(String[] args) {
        CarEcoRating ecoRating = new CarEcoRating();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Тип топлива (Бензин, Дизель, Гибрид, Электро):");
        String type = scanner.next();
        System.out.println("Объем двигателя (л):");
        double volume = scanner.nextDouble();
        System.out.println("Расход топлива (л/100км или кВтч/100км):");
        double consumption = scanner.nextDouble();
        System.out.println("Год выпуска:");
        int year = scanner.nextInt();
        System.out.println("Соответствует стандарту Евро-6 (да/нет):");
        String euro = scanner.next();
        boolean isEuro = euro.equalsIgnoreCase("да");

        int rating = ecoRating.calculateEcoRating(type, volume, consumption, year, isEuro);
        if (rating == ERROR_CODE) {
            System.out.println("Ошибка расчета рейтинга");
        } else {
            System.out.println("Экологический рейтинг: " + rating);
        }

        scanner.close();
    }
}
