package ru.mentee.power.conditions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCalculatorTest {
    @Test
    @DisplayName("Расчет платежа для ипотеки с плохим кредитным рейтингом")
    void calculateMortgageWithPoorCreditScore() {
        // Arrange
        double loanAmount = 5_000_000;
        int loanTermMonths = 240; // 20 лет
        String creditType = "Ипотека";
        int creditScore = 400; // Плохой рейтинг
        CreditCalculator calculator=new CreditCalculator();
        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для плохого рейтинга (300-499) ставка по ипотеке: 9% + 3% = 12%
        double monthlyRate = 12.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(0.01));
    }

    @Test
    @DisplayName("Расчет платежа для автокредита с отличным кредитным рейтингом")
    void calculateAutoLoanWithExcellentCreditScore() {
        // Arrange
        double loanAmount = 800_000;
        int loanTermMonths = 48; // 4 года
        String creditType = "Автокредит";
        int creditScore = 800; // Отличный рейтинг
        CreditCalculator calculator=new CreditCalculator();
        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для отличного рейтинга (750-850) ставка по автокредиту: 12% - 2% = 10%
        double monthlyRate = 10.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(84.0));
    }

    @Test
    @DisplayName("Расчет платежа для потребительского кредита с средним кредитным рейтингом")
    void calculateConsumerLoanWithAverageCreditScore() {
        // Arrange
        double loanAmount = 500_000;
        int loanTermMonths = 36; // 3 года
        String creditType = "Потребительский";
        int creditScore = 600; // Средний рейтинг
        CreditCalculator calculator=new CreditCalculator();
        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для среднего рейтинга (500-649) ставка по потребительскому кредиту: 15%
        double monthlyRate = 15.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(84.0));
    }

    @Test
    @DisplayName("Расчет платежа для кредита с минимальным сроком")
    void calculateLoanWithMinTerm() {
        // Arrange
        double loanAmount = 100_000;
        int loanTermMonths = 1; // Минимальный срок
        String creditType = "Потребительский";
        int creditScore = 700;
        CreditCalculator calculator=new CreditCalculator();
        // Act
        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для потребительского кредита со средним рейтингом ставка: 15%
        double monthlyRate = 15.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertThat(monthlyPayment).isCloseTo(expectedPayment, within(84.0));
    }

    @Test
    @DisplayName("Расчет платежа для кредита с максимальным сроком")
    void calculateLoanWithMaxTerm() {
        // Arrange
        double loanAmount = 1_000_000;
        int loanTermMonths = 360; // Максимальный срок
        String creditType = "Ипотека";
        int creditScore = 750;

        CreditCalculator calculator=new CreditCalculator();
        // Act

        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // Assert
        // Для ипотеки с отличным рейтингом ставка: 9% - 2% = 7%
        double monthlyRate = 7.0 / 12 / 100;
        double expectedPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths))
                / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);
}

    @Test
    @DisplayName("Расчет платежа для ипотеки с максимальным сроком")
    public void testMortgage_MinCreditScore() {
        CreditCalculator calculator = new CreditCalculator();
        double loanAmount = 10_000_000; // максимальная сумма кредита
        int loanTermMonths = 360; // максимальный срок кредита
        String creditType = "Ипотека";
        int creditScore = 310; // минимальный кредитный рейтинг

        double monthlyPayment = calculator.calculateMonthlyPayment(loanAmount, loanTermMonths, creditType, creditScore);

        // расчет ежемесячного платежа вручную
        double baseRate = 9.0; // базовая процентная ставка для ипотеки
        double rateAdjustment = 3.0; // корректировка процентной ставки для плохого кредитного рейтинга
        double annualRate = baseRate + rateAdjustment;
        double monthlyRate = annualRate / 12 / 100;
        double expectedMonthlyPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, loanTermMonths)) / (Math.pow(1 + monthlyRate, loanTermMonths) - 1);

        assertEquals(expectedMonthlyPayment, monthlyPayment, 84);
    }
}

