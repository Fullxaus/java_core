package ru.mentee.power.conditions;

import java.util.Scanner;

public class WeekdayDeterminer {

    /**
     * Возвращает описание дня недели.
     *
     * @param day номер дня недели (1-7)
     * @return Строка с названием дня, типом (рабочий/выходной) и доп. информацией,
     *         или "Некорректный день недели", если номер вне диапазона 1-7.
     */
    public static String getDayDescription(int day) {
        String dayName;
        String dayType;
        String additionalInfo = "";

        switch (day) {
            case 1:
                dayName = "Понедельник";
                dayType = "рабочий день";
                additionalInfo = "Тяжелый день";
                break;
            case 2:
                dayName = "Вторник";
                dayType = "рабочий день";
                break;
            case 3:
                dayName = "Среда";
                dayType = "рабочий день";
                additionalInfo = "Середина недели";
                break;
            case 4:
                dayName = "Четверг";
                dayType = "рабочий день";
                break;
            case 5:
                dayName = "Пятница";
                dayType = "рабочий день";
                additionalInfo = "Скоро выходные!";
                break;
            case 6:
                dayName = "Суббота";
                dayType = "выходной";
                break;
            case 7:
                dayName = "Воскресенье";
                dayType = "выходной";
                break;
            default:
                return "Некорректный день недели";
        }

        String result = dayName + " - " + dayType;
        if (!additionalInfo.isEmpty()) {
            result += "\n" + additionalInfo;
        }
        return result;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите номер дня недели (1-7): ");
        int day = scanner.nextInt();
        String description = getDayDescription(day); // Вызываем наш метод
        System.out.println(description);

        scanner.close();
    }
}
