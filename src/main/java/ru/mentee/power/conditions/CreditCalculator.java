package ru.mentee.power.conditions;

import java.util.Scanner;

/**
 * Класс для расчёта ежемесячного платежа по кредиту
 * с учётом типа кредита и кредитного рейтинга клиента.
 */
public class CreditCalculator {

    private static final double MORTGAGE_RATE = 9.0;
    private static final double CONSUMER_LOAN_RATE = 15.0;
    private static final double AUTO_LOAN_RATE = 12.0;

    private static final int EXCELLENT_CREDIT_SCORE_MIN = 750;
    private static final int EXCELLENT_CREDIT_SCORE_MAX = 850;
    private static final int GOOD_CREDIT_SCORE_MIN = 650;
    private static final int GOOD_CREDIT_SCORE_MAX = 749;
    private static final int AVERAGE_CREDIT_SCORE_MIN = 500;
    private static final int AVERAGE_CREDIT_SCORE_MAX = 649;
    private static final int POOR_CREDIT_SCORE_MIN = 300;
    private static final int POOR_CREDIT_SCORE_MAX = 499;

    private static final double MIN_LOAN_AMOUNT = 10_000.0;
    private static final double MAX_LOAN_AMOUNT = 10_000_000.0;
    private static final int MIN_LOAN_TERM_MONTHS = 1;
    private static final int MAX_LOAN_TERM_MONTHS = 360;

    /**
     * Рассчитывает ежемесячный платёж по кредиту.
     *
     * @param loanAmount     сумма кредита (от 10 000 до 10 000 000 руб.)
     * @param loanTermMonths срок кредита в месяцах (от 1 до 360)
     * @param creditType     тип кредита ("Ипотека", "Потребительский", "Автокредит")
     * @param creditScore    кредитный рейтинг клиента (от 300 до 850)
     * @return ежемесячный платёж или -1 при некорректных входных данных
     */
    public double calculateMonthlyPayment(double loanAmount,
                                          int loanTermMonths,
                                          String creditType,
                                          int creditScore) {
        if (!isValidLoanAmount(loanAmount)
                || !isValidLoanTerm(loanTermMonths)
                || !isValidCreditScore(creditScore)) {
            return -1;
        }

        double baseRate = determineBaseRate(creditType);
        if (baseRate < 0) {
            return -1;
        }

        double annualRate = adjustRateByCreditScore(baseRate, creditScore);
        double monthlyRate = annualRate / 12.0 / 100.0;

        return loanAmount
                * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
    }

    private boolean isValidLoanAmount(double amount) {
        return amount >= MIN_LOAN_AMOUNT && amount <= MAX_LOAN_AMOUNT;
    }

    private boolean isValidLoanTerm(int months) {
        return months >= MIN_LOAN_TERM_MONTHS
                && months <= MAX_LOAN_TERM_MONTHS;
    }

    private boolean isValidCreditScore(int score) {
        return score >= POOR_CREDIT_SCORE_MIN
                && score <= EXCELLENT_CREDIT_SCORE_MAX;
    }

    private double determineBaseRate(String creditType) {
        switch (creditType) {
            case "Ипотека":
                return MORTGAGE_RATE;
            case "Потребительский":
                return CONSUMER_LOAN_RATE;
            case "Автокредит":
                return AUTO_LOAN_RATE;
            default:
                return -1;
        }
    }

    private double adjustRateByCreditScore(double baseRate, int creditScore) {
        double adjustment;
        if (creditScore >= EXCELLENT_CREDIT_SCORE_MIN
                && creditScore <= EXCELLENT_CREDIT_SCORE_MAX) {
            adjustment = -2.0;
        } else if (creditScore >= GOOD_CREDIT_SCORE_MIN
                && creditScore <= GOOD_CREDIT_SCORE_MAX) {
            adjustment = -1.0;
        } else if (creditScore >= AVERAGE_CREDIT_SCORE_MIN
                && creditScore <= AVERAGE_CREDIT_SCORE_MAX) {
            adjustment = 0.0;
        } else {
            adjustment = 3.0;
        }
        return baseRate + adjustment;
    }

    /**
     * Точка входа для консольного запуска.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CreditCalculator calculator = new CreditCalculator();

        System.out.print("Введите сумму кредита (от 10 000 до 10 000 000 руб.): ");
        double loanAmount = scanner.nextDouble();

        System.out.print("Введите срок кредита в месяцах (от 1 до 360): ");
        int loanTermMonths = scanner.nextInt();

        System.out.print("Введите тип кредита (Ипотека, Потребительский, Автокредит): ");
        String creditType = scanner.next();

        System.out.print("Введите кредитный рейтинг клиента (от 300 до 850): ");
        int creditScore = scanner.nextInt();

        double payment = calculator.calculateMonthlyPayment(
                loanAmount, loanTermMonths, creditType, creditScore);

        if (payment < 0) {
            System.out.println("Некорректные входные данные");
        } else {
            System.out.printf("Ежемесячный платёж: %.2f руб.%n", payment);
        }

        scanner.close();
    }
}