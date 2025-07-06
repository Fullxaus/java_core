package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UnitConverter {

    // Код ошибки
    private static final double ERROR_CODE = -1.0;

    // Списки поддерживаемых единиц
    private static final List<String> LENGTH_UNITS = Arrays.asList(
            "Метр", "Сантиметр", "Дюйм", "Фут"
    );
    private static final List<String> WEIGHT_UNITS = Arrays.asList(
            "Килограмм", "Грамм", "Фунт", "Унция"
    );
    private static final List<String> TEMP_UNITS = Arrays.asList(
            "Цельсий", "Фаренгейт", "Кельвин"
    );

    /**
     * Главный метод конвертации.
     */
    public double convert(double value, String fromUnit, String toUnit) {
        // Проверяем поддержку единиц
        if (!isSupported(fromUnit) || !isSupported(toUnit)) {
            return ERROR_CODE;
        }
        // Проверяем, что они из одной категории
        if (!areSameCategory(fromUnit, toUnit)) {
            return ERROR_CODE;
        }
        String category = getCategory(fromUnit);
        switch (category) {
            case "Длина":
                return convertLength(value, fromUnit, toUnit);
            case "Вес":
                return convertWeight(value, fromUnit, toUnit);
            case "Температура":
                return convertTemperature(value, fromUnit, toUnit);
            default:
                return ERROR_CODE;
        }
    }

    // Проверка, что единица поддерживается хотя бы в одном списке
    private boolean isSupported(String unit) {
        return LENGTH_UNITS.contains(unit)
                || WEIGHT_UNITS.contains(unit)
                || TEMP_UNITS.contains(unit);
    }

    // Проверка, что обе единицы из одной категории
    private boolean areSameCategory(String unit1, String unit2) {
        String c1 = getCategory(unit1);
        String c2 = getCategory(unit2);
        return c1 != null && c1.equals(c2);
    }

    // Определение категории по единице
    private String getCategory(String unit) {
        if (LENGTH_UNITS.contains(unit))  return "Длина";
        if (WEIGHT_UNITS.contains(unit))  return "Вес";
        if (TEMP_UNITS.contains(unit))    return "Температура";
        return null;
    }

    // Конвертация длин: базовая единица — метр
    private double convertLength(double value, String fromUnit, String toUnit) {
        // В переводе в метры
        double inMeters;
        switch (fromUnit) {
            case "Метр":        inMeters = value; break;
            case "Сантиметр":   inMeters = value / 100.0; break;
            case "Дюйм":        inMeters = value / 39.37; break;
            case "Фут":         inMeters = value / 3.28; break;
            default:            return ERROR_CODE;
        }
        // Из метров в целевую
        switch (toUnit) {
            case "Метр":        return inMeters;
            case "Сантиметр":   return inMeters * 100.0;
            case "Дюйм":        return inMeters * 39.37;
            case "Фут":         return inMeters * 3.28;
            default:            return ERROR_CODE;
        }
    }

    // Конвертация веса: базовая единица — килограмм
    private double convertWeight(double value, String fromUnit, String toUnit) {
        // В переводе в килограммы
        double inKg;
        switch (fromUnit) {
            case "Килограмм":   inKg = value; break;
            case "Грамм":       inKg = value / 1000.0; break;
            case "Фунт":        inKg = value / 2.20462; break;
            case "Унция":       inKg = value / 35.274; break;
            default:            return ERROR_CODE;
        }
        // Из килограммов в целевую
        switch (toUnit) {
            case "Килограмм":   return inKg;
            case "Грамм":       return inKg * 1000.0;
            case "Фунт":        return inKg * 2.20462;
            case "Унция":       return inKg * 35.274;
            default:            return ERROR_CODE;
        }
    }

    // Конвертация температуры «напрямую»
    private double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value;
        }
        double result;
        // Сначала приводим всё к Цельсию
        double celsius;
        switch (fromUnit) {
            case "Цельсий":
                celsius = value;
                break;
            case "Фаренгейт":
                celsius = (value - 32) * 5.0 / 9.0;
                break;
            case "Кельвин":
                celsius = value - 273.15;
                break;
            default:
                return ERROR_CODE;
        }
        // Затем из Цельсия в нужную
        switch (toUnit) {
            case "Цельсий":
                result = celsius;
                break;
            case "Фаренгейт":
                result = celsius * 9.0 / 5.0 + 32;
                break;
            case "Кельвин":
                result = celsius + 273.15;
                break;
            default:
                return ERROR_CODE;
        }
        return result;
    }

    public static void main(String[] args) {
        UnitConverter converter = new UnitConverter();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите значение: ");
        double val = scanner.nextDouble();
        System.out.print("Введите исходную единицу (например, \"Метр\"): ");
        String from = scanner.next();
        System.out.print("Введите целевую единицу (например, \"Фут\"): ");
        String to = scanner.next();

        double result = converter.convert(val, from, to);
        if (result == ERROR_CODE) {
            System.out.println("Ошибка конвертации!");
        } else {
            System.out.printf("Результат: %.6f%n", result);
        }

        scanner.close();
    }
}