package ru.mentee.power.variables;

public class TemperatureConverter {
    // Константы для ключевых значений
    static final int ABSOLUTE_ZERO_C = -273;
    static final int ABSOLUTE_ZERO_K = 0;

    public static void main(String[] args) {
        // Переменные с температурами в разных шкалах
        double celsius = 25.0;       // в Цельсиях
        double fahrenheit = 77.0;    // в Фаренгейтах
        double kelvin = 300.0;       // в Кельвинах

        // Конвертация
        double cToF = (celsius * 9 / 5) + 32;
        double fToC = (fahrenheit - 32) * 5 / 9;
        double cToK = celsius + 273.0;
        double kToC = kelvin - 273.0;

        // Вывод результатов
        System.out.println("Температуры:");
        System.out.println(celsius + " °C в Фаренгейты = " + cToF + " °F");
        System.out.println(fahrenheit + " °F в Цельсии = " + fToC + " °C");
        System.out.println(celsius + " °C в Кельвины = " + cToK + " K");
        System.out.println(kelvin + " K в Цельсии = " + kToC + " °C");
    }
}
