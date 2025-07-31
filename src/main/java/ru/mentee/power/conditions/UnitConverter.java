package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnitConverter {

    // Логгер вместо прямого System.out/System.err
    private static final Logger LOGGER = Logger.getLogger(UnitConverter.class.getName());

    // Код ошибки
    private static final double ERROR_CODE = -1.0;

    // Категории
    private static final String CATEGORY_LENGTH      = "Длина";
    private static final String CATEGORY_WEIGHT      = "Вес";
    private static final String CATEGORY_TEMPERATURE = "Температура";

    // Названия единиц длины
    private static final String UNIT_METER      = "Метр";
    private static final String UNIT_CENTIMETER = "Сантиметр";
    private static final String UNIT_INCH       = "Дюйм";
    private static final String UNIT_FOOT       = "Фут";

    // Названия единиц веса
    private static final String UNIT_KILOGRAM = "Килограмм";
    private static final String UNIT_GRAM     = "Грамм";
    private static final String UNIT_POUND    = "Фунт";
    private static final String UNIT_OUNCE    = "Унция";

    // Названия единиц температуры
    private static final String UNIT_CELSIUS    = "Цельсий";
    private static final String UNIT_FAHRENHEIT = "Фаренгейт";
    private static final String UNIT_KELVIN     = "Кельвин";

    // Списки поддерживаемых единиц
    private static final List<String> LENGTH_UNITS = Arrays.asList(
            UNIT_METER, UNIT_CENTIMETER, UNIT_INCH, UNIT_FOOT
    );
    private static final List<String> WEIGHT_UNITS = Arrays.asList(
            UNIT_KILOGRAM, UNIT_GRAM, UNIT_POUND, UNIT_OUNCE
    );
    private static final List<String> TEMP_UNITS = Arrays.asList(
            UNIT_CELSIUS, UNIT_FAHRENHEIT, UNIT_KELVIN
    );

    /**
     * Главный метод конвертации.
     * Возвращает ERROR_CODE в случае ошибки.
     */
    public double convert(Double value, String fromUnit, String toUnit) {
        // Защита от null
        if (value == null || fromUnit == null || toUnit == null) {
            LOGGER.warning("Один из аргументов convert() равен null");
            return ERROR_CODE;
        }

        // Поддерживаются ли указанные единицы?
        if (!isSupported(fromUnit) || !isSupported(toUnit)) {
            LOGGER.warning("Неподдерживаемая единица: from=" + fromUnit + ", to=" + toUnit);
            return ERROR_CODE;
        }

        // Одинаковая ли категория?
        if (!areSameCategory(fromUnit, toUnit)) {
            LOGGER.warning("Попытка конвертировать между разными категориями: from="
                    + fromUnit + ", to=" + toUnit);
            return ERROR_CODE;
        }

        String category = getCategory(fromUnit);
        if (category == null) {
            // В теории сюда мы не попадём, потому что isSupported и areSameCategory уже отфильтровали null
            LOGGER.severe("Не удалось определить категорию для единицы " + fromUnit);
            return ERROR_CODE;
        }

        switch (category) {
            case CATEGORY_LENGTH:
                return convertLength(value, fromUnit, toUnit);
            case CATEGORY_WEIGHT:
                return convertWeight(value, fromUnit, toUnit);
            case CATEGORY_TEMPERATURE:
                return convertTemperature(value, fromUnit, toUnit);
            default:
                LOGGER.severe("Неизвестная категория: " + category);
                return ERROR_CODE;
        }
    }

    private boolean isSupported(String unit) {
        return LENGTH_UNITS.contains(unit)
                || WEIGHT_UNITS.contains(unit)
                || TEMP_UNITS.contains(unit);
    }

    private boolean areSameCategory(String unit1, String unit2) {
        // Objects.equals защищает от NPE
        String c1 = getCategory(unit1);
        String c2 = getCategory(unit2);
        return c1 != null && Objects.equals(c1, c2);
    }

    private String getCategory(String unit) {
        if (LENGTH_UNITS.contains(unit)) {
            return CATEGORY_LENGTH;
        }
        if (WEIGHT_UNITS.contains(unit)) {
            return CATEGORY_WEIGHT;
        }
        if (TEMP_UNITS.contains(unit)) {
            return CATEGORY_TEMPERATURE;
        }
        return null;
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        double inMeters;
        switch (fromUnit) {
            case UNIT_METER:
                inMeters = value;
                break;
            case UNIT_CENTIMETER:
                inMeters = value / 100.0;
                break;
            case UNIT_INCH:
                inMeters = value / 39.37;
                break;
            case UNIT_FOOT:
                inMeters = value / 3.28;
                break;
            default:
                return ERROR_CODE;
        }

        switch (toUnit) {
            case UNIT_METER:
                return inMeters;
            case UNIT_CENTIMETER:
                return inMeters * 100.0;
            case UNIT_INCH:
                return inMeters * 39.37;
            case UNIT_FOOT:
                return inMeters * 3.28;
            default:
                return ERROR_CODE;
        }
    }

    private double convertWeight(double value, String fromUnit, String toUnit) {
        double inKg;
        switch (fromUnit) {
            case UNIT_KILOGRAM:
                inKg = value;
                break;
            case UNIT_GRAM:
                inKg = value / 1000.0;
                break;
            case UNIT_POUND:
                inKg = value / 2.20462;
                break;
            case UNIT_OUNCE:
                inKg = value / 35.274;
                break;
            default:
                return ERROR_CODE;
        }

        switch (toUnit) {
            case UNIT_KILOGRAM:
                return inKg;
            case UNIT_GRAM:
                return inKg * 1000.0;
            case UNIT_POUND:
                return inKg * 2.20462;
            case UNIT_OUNCE:
                return inKg * 35.274;
            default:
                return ERROR_CODE;
        }
    }

    private double convertTemperature(double value, String fromUnit, String toUnit) {
        // Objects.equals защищает от NPE
        if (Objects.equals(fromUnit, toUnit)) {
            return value;
        }

        double celsius;
        switch (fromUnit) {
            case UNIT_CELSIUS:
                celsius = value;
                break;
            case UNIT_FAHRENHEIT:
                celsius = (value - 32) * 5.0 / 9.0;
                break;
            case UNIT_KELVIN:
                celsius = value - 273.15;
                break;
            default:
                return ERROR_CODE;
        }

        switch (toUnit) {
            case UNIT_CELSIUS:
                return celsius;
            case UNIT_FAHRENHEIT:
                return celsius * 9.0 / 5.0 + 32;
            case UNIT_KELVIN:
                return celsius + 273.15;
            default:
                return ERROR_CODE;
        }
    }

    public static void main(String[] args) {
        UnitConverter converter = new UnitConverter();
        Scanner scanner = new Scanner(System.in);

        LOGGER.info("Введите значение:");
        Double val = null;
        try {
            val = scanner.nextDouble();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка чтения числа", e);
            System.exit(1);
        }
        scanner.nextLine(); // сброс остатка строки

        LOGGER.info("Введите исходную единицу (например, \"" + UNIT_METER + "\"):");
        String from = scanner.nextLine().trim();

        LOGGER.info("Введите целевую единицу (например, \"" + UNIT_FOOT + "\"):");
        String to = scanner.nextLine().trim();

        double result = converter.convert(val, from, to);
        if (result == ERROR_CODE) {
            LOGGER.severe("Ошибка конвертации!");
        } else {
            LOGGER.info(String.format("Результат: %.6f", result));
        }

        scanner.close();
    }
}
