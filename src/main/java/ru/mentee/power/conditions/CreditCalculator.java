package ru.mentee.power.conditions;

import java.util.Scanner;

public class CreditCalculator {

    // Базовые процентные ставки по типам кредитов
    private static final double MORTGAGE_RATE = 9.0;
    private static final double CONSUMER_LOAN_RATE = 15.0;
    private static final double AUTO_LOAN_RATE = 12.0;

    // Диапазоны кредитного рейтинга
    private static final int EXCELLENT_CREDIT_SCORE_MIN = 750;
    private static final int EXCELLENT_CREDIT_SCORE_MAX = 850;
    private static final int GOOD_CREDIT_SCORE_MIN = 650;
    private static final int GOOD_CREDIT_SCORE_MAX = 749;
    private static final int AVERAGE_CREDIT_SCORE_MIN = 500;
    private static final int AVERAGE_CREDIT_SCORE_MAX = 649;
    private static final int POOR_CREDIT_SCORE_MIN = 300;
    private static final int POOR_CREDIT_SCORE_MAX = 499;

    // Диапазоны суммы кредита и срока кредита
    private static final double MIN_LOAN_AMOUNT = 10_000;
    private static final double MAX_LOAN_AMOUNT = 10_000_000;
    private static final int MIN_LOAN_TERM_MONTHS = 1;
    private static final int MAX_LOAN_TERM_MONTHS = 360;

    /**
     * Рассчитывает ежемесячный платеж по кредиту
     *
     * @param loanAmount     сумма кредита (от 10,000 до 10,000,000 руб.)
     * @param loanTermMonths срок кредита в месяцах (от 1 до 360)
     * @param creditType     тип кредита ("Ипотека", "Потребительский", "Автокредит")
     * @param creditScore    кредитный рейтинг клиента (от 300 до 850)
     * @return ежемесячный платеж или -1 в случае некорректных входных данных
     */
    public double calculateMonthlyPayment(double loanAmount, int loanTermMonths, String creditType, int creditScore) {
        // Проверка входных данных
        if (loanAmount < MIN_LOAN_AMOUNT || loanAmount > MAX_LOAN_AMOUNT) {
            return -1;
        }
        if (loanTermMonths < MIN_LOAN_TERM_MONTHS || loanTermMonths > MAX_LOAN_TERM_MONTHS) {
            return -1;
        }
        if (creditScore < 300 || creditScore > 850) {
            return -1;
        }

        // Определение базовой процентной ставки по типу кредита
        double baseRate;
        switch (creditType) {
            case "Ипотека":
                baseRate = MORTGAGE_RATE;
                break;
            case "Потребительский":
                baseRate = CONSUMER_LOAN_RATE;
                break;
            case "Автокредит":
                baseRate = AUTO_LOAN_RATE;
                break;
            default:
                return -1;
        }

        // Корректировка процентной ставки в зависимости от кредитного рейтинга
        double rateAdjustment;
        if (creditScore >= EXCELLENT_CREDIT_SCORE_MIN && creditScore <= EXCELLENT_CREDIT_SCORE_MAX) {
            rateAdjustment = -2.0;
        } else if (creditScore >= GOOD_CREDIT_SCORE_MIN && creditScore <= GOOD_CREDIT_SCORE_MAX) {
            rateAdjustment = -1.0;
        } else if (creditScore >= AVERAGE_CREDIT_SCORE_MIN && creditScore <= AVERAGE_CREDIT_SCORE_MAX) {
            rateAdjustment = 0.0;
        } else {
            rateAdjustment = 3.0;
        }
        double annualRate = baseRate + rateAdjustment;

        // Расчет ежемесячного платежа
        double monthlyRate = annualRate / 12 / 100;
        double monthlyPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths)) / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        return monthlyPayment;
    }

    public static void main(String[] args) {
        CreditCalculator calculator = new CreditCalculator();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите сумму кредита (от 10,000 до 10,000,000 руб.): ");
        double loanAmount = scanner.nextDouble();

        System.out.print("Введите срок кредита в месяцах (от 1 до 360): ");
        int loanTermMonths = scanner.nextInt();

        System.out.print("Введите тип кредита (Ипотека, Потребительский, Автокредит): ");
        String creditType = scanner.next();

        System.out.print("Введите кредитный рейтинг клиента (от 300 до 850): ");
        int creditScore = scanner.nextInt();

        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        if (monthlyPayment == -1) {
            System.out.println("Некорректные входные данные");
        } else {
            System.out.printf("Ежемесячный платеж: %.2f руб.%n", monthlyPayment);
        }

        scanner.close();
    }
}