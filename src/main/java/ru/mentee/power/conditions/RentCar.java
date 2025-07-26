package ru.mentee.power.conditions;

import java.util.Scanner;

/**
 * Класс проверяет, может ли человек арендовать автомобиль
 * на основании его возраста.
 */
public class RentCar {

    /** Возраст пользователя. */
    private static int userAge;

    /**
     * Устанавливает возраст пользователя для тестирования.
     */
    public static void setUserAge(int age) {
        userAge = age;
    }

    /** Считывает возраст пользователя из консоли. */
    private static void readUserAge() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите возраст человека: ");
        if (scanner.hasNextInt()) {
            userAge = scanner.nextInt();
        } else {
            System.out.println("Ошибка: введено не целое число.");
            userAge = -1;
        }
        scanner.close();
    }

    /** Печатает сообщение о возможности аренды через if-else. */
    public static void rentCarWithIf() {
        if (userAge >= 18) {
            System.out.println("Вы можете арендовать автомобиль.");
        } else {
            System.out.println("Вы не можете арендовать автомобиль.");
        }
    }

    /** Печатает сообщение о возможности аренды с помощью тернарного оператора. */
    public static void rentCarWithTernary() {
        String message = (userAge >= 18)
                ? "Вы можете арендовать автомобиль."
                : "Вы не можете арендовать автомобиль.";
        System.out.println(message);
    }

    /** Точка входа в программу. */
    public static void main(String[] args) {
        readUserAge();
        rentCarWithIf();
        rentCarWithTernary();
    }
}
