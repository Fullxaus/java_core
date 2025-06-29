package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;

public class SmartThermostat {

    private static final double ERROR_TEMP_CODE = -100.0;
    private static final List<String> WEEKDAYS = Arrays.asList("Понедельник", "Вторник", "Среда", "Четверг", "Пятница");
    private static final List<String> WEEKENDS = Arrays.asList("Суббота", "Воскресенье");

    public double getTargetTemperature(int timeOfDay, String dayOfWeek, boolean isOccupied, double currentOutsideTemperature) {
        if (timeOfDay < 0 || timeOfDay > 23) {
            return ERROR_TEMP_CODE;
        }

        if (!WEEKDAYS.contains(dayOfWeek) && !WEEKENDS.contains(dayOfWeek)) {
            return ERROR_TEMP_CODE;
        }

        double baseTemperature;
        if (WEEKDAYS.contains(dayOfWeek)) {
            baseTemperature = getTemperatureForWeekday(timeOfDay, isOccupied);
        } else {
            baseTemperature = getTemperatureForWeekend(timeOfDay, isOccupied);
        }

        if (currentOutsideTemperature > 25) {
            baseTemperature += 1;
        } else if (currentOutsideTemperature < 0) {
            baseTemperature -= 1;
        }

        return baseTemperature;
    }

    private double getTemperatureForWeekday(int timeOfDay, boolean isOccupied) {
        if (timeOfDay >= 6 && timeOfDay <= 8) {
            return isOccupied ? 22 : 18;
        } else if (timeOfDay >= 9 && timeOfDay <= 17) {
            return isOccupied ? 20 : 16;
        } else if (timeOfDay >= 18 && timeOfDay <= 22) {
            return isOccupied ? 22 : 17;
        } else {
            return isOccupied ? 19 : 16;
        }
    }

    private double getTemperatureForWeekend(int timeOfDay, boolean isOccupied) {
        if (timeOfDay >= 7 && timeOfDay <= 9) {
            return isOccupied ? 23 : 18;
        } else if (timeOfDay >= 10 && timeOfDay <= 17) {
            return isOccupied ? 22 : 17;
        } else if (timeOfDay >= 18 && timeOfDay <= 23) {
            return isOccupied ? 22 : 17;
        } else {
            return isOccupied ? 20 : 16;
        }
    }

    public static void main(String[] args) {
        SmartThermostat thermostat = new SmartThermostat();
        // Example usage
        int timeOfDay = 12;
        String dayOfWeek = "Понедельник";
        boolean isOccupied = true;
        double outsideTemp = 20.0;

        double targetTemp = thermostat.getTargetTemperature(timeOfDay, dayOfWeek, isOccupied, outsideTemp);
        if (targetTemp == ERROR_TEMP_CODE) {
            System.out.println("Ошибка: Некорректные входные данные.");
        } else {
            System.out.println("Рекомендуемая температура: " + targetTemp + "°C");
        }
    }
}
