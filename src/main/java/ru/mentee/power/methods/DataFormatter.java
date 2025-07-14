package ru.mentee.power.methods;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * Класс для форматирования различных типов данных в строку.
 */
public class DataFormatter {

    // Общие символы для форматирования: пробел как разделитель тысяч и запятая как десятичный разделитель
    private static final DecimalFormatSymbols CUSTOM_SYMBOLS;
    static {
        CUSTOM_SYMBOLS = new DecimalFormatSymbols(Locale.getDefault());
        CUSTOM_SYMBOLS.setGroupingSeparator(' ');
        CUSTOM_SYMBOLS.setDecimalSeparator(',');
    }

    /**
     * Форматирует целое число, разделяя его пробелами по тысячам.
     *
     * @param value Целое число
     * @return Отформатированное представление числа, например "1 234 567"
     */
    public static String format(int value) {
        DecimalFormat df = new DecimalFormat("#,##0", CUSTOM_SYMBOLS);
        return df.format(value);
    }

    /**
     * Форматирует целое число с указанием префикса и суффикса.
     *
     * @param value  Целое число
     * @param prefix Префикс (например, символ валюты "$")
     * @param suffix Суффикс (например, код валюты "USD")
     * @return Отформатированное представление, например "$1 234 567 USD"
     */
    public static String format(int value, String prefix, String suffix) {
        String core = format(value);
        StringBuilder sb = new StringBuilder();
        if (prefix != null && !prefix.isEmpty()) {
            sb.append(prefix);
        }
        sb.append(core);
        if (suffix != null && !suffix.isEmpty()) {
            sb.append(suffix);
        }
        return sb.toString();
    }

    /**
     * Форматирует число с плавающей точкой, используя два десятичных знака.
     *
     * @param value Число с плавающей точкой
     * @return Отформатированное представление, например "1 234,56"
     */
    public static String format(double value) {
        return format(value, 2);
    }

    /**
     * Форматирует число с плавающей точкой с указанным количеством десятичных знаков.
     *
     * @param value         Число с плавающей точкой
     * @param decimalPlaces Количество знаков после запятой
     * @return Отформатированное представление, например "1 234,5678"
     */
    public static String format(double value, int decimalPlaces) {
        // Строим шаблон: "#,##0." + нужное количество нулей
        StringBuilder pattern = new StringBuilder("#,##0");
        if (decimalPlaces > 0) {
            pattern.append('.');
            for (int i = 0; i < decimalPlaces; i++) {
                pattern.append('0');
            }
        }
        DecimalFormat df = new DecimalFormat(pattern.toString(), CUSTOM_SYMBOLS);
        return df.format(value);
    }

    /**
     * Форматирует дату в формате "dd.MM.yyyy".
     *
     * @param date Дата
     * @return Отформатированная строка, например "01.01.2023"
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    /**
     * Форматирует дату согласно указанному шаблону SimpleDateFormat.
     *
     * @param date    Дата
     * @param pattern Шаблон, например "dd MMMM yyyy"
     * @return Отформатированная строка, например "01 января 2023"
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null || pattern.isEmpty()) {
            // Если шаблон пустой, возвращаем ISO-представление
            return date.toString();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * Форматирует список строк, объединяя их через запятую и пробел.
     *
     * @param items Список строк
     * @return Объединенная строка, например "apple, banana, orange"
     */
    public static String format(List<String> items) {
        return format(items, ", ");
    }

    /**
     * Форматирует список строк, объединяя их через указанный разделитель.
     *
     * @param items     Список строк
     * @param delimiter Разделитель, например " | "
     * @return Объединенная строка, например "apple | banana | orange"
     */
    public static String format(List<String> items, String delimiter) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        if (delimiter == null) {
            delimiter = "";
        }
        StringJoiner joiner = new StringJoiner(delimiter);
        for (String item : items) {
            if (item != null) {
                joiner.add(item);
            }
        }
        return joiner.toString();
    }

}
