package ru.mentee.power.conditions;

import java.util.Scanner;

public class TheaterTickets {

    public static final int BASE_TICKET_PRICE = 1000;

    /**
     * Рассчитывает итоговую стоимость билета с учетом скидок.
     *
     * @param age       Возраст посетителя.
     * @param isStudent Является ли студентом.
     * @param isWeekend Является ли день выходным.
     * @return Итоговая стоимость билета.
     */
    public static double calculateTicketPrice(int age, boolean isStudent, boolean isWeekend) {
        double discount = 0.0;

        if (age < 6) {
            discount = 1.0;
        } else {
            if (age <= 18) {
                discount = Math.max(discount, 0.50);
            }
            if (age > 65) {
                discount = Math.max(discount, 0.30);
            }
            if (isStudent && !isWeekend) {
                discount = Math.max(discount, 0.25);
            }
        }
        return BASE_TICKET_PRICE * (1.0 - discount);
    }

    /**
     * Рассчитывает размер примененной скидки в процентах.
     */
    public static double getAppliedDiscountPercentage(int age, boolean isStudent, boolean isWeekend) {
        double finalPrice = calculateTicketPrice(age, isStudent, isWeekend);
        return ((double)(BASE_TICKET_PRICE - finalPrice) / BASE_TICKET_PRICE) * 100;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();

        System.out.print("У вас есть студенческий билет? (true/false): ");
        boolean isStudent = scanner.nextBoolean();

        System.out.print("Сегодня выходной день? (true/false): ");
        boolean isWeekend = scanner.nextBoolean();

        double finalPrice = calculateTicketPrice(age, isStudent, isWeekend);
        double discountPercentage = getAppliedDiscountPercentage(age, isStudent, isWeekend);
        double discountAmount = BASE_TICKET_PRICE - finalPrice;


        System.out.printf("Базовая стоимость билета: %d руб.%n", BASE_TICKET_PRICE);
        if (discountPercentage > 0) {
            System.out.printf("Ваша скидка: %.0f%% (%.0f руб.)%n", discountPercentage, discountAmount);
        } else {
            System.out.println("Скидка не применена.");
        }
        System.out.printf("Итоговая стоимость билета: %.0f руб.%n", finalPrice);


        scanner.close();
    }
}
