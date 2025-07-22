package ru.mentee.power.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.offset;

@DisplayName("Тесты для DiscountCalculator")
public class DiscountCalculatorTest {

    private DiscountCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new DiscountCalculator();
    }

    @Test
    @DisplayName("Скидка 0% для суммы <= 1000")
    void shouldApplyZeroDiscountForAmountLessOrEqual1000() {
        double amount = 800.0;
        double expectedPrice = 800.0;

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Скидка 10% для суммы > 1000 и <= 5000")
    void shouldApply10PercentDiscountForAmountBetween1000And5000() {
        double amount = 1200.0;
        double expectedPrice = 1200.0 * 0.9; // 1080.0

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Скидка 20% для суммы > 5000")
    void shouldApply20PercentDiscountForAmountGreaterThan5000() {
        double amount = 6000.0;
        double expectedPrice = 6000.0 * 0.8; // 4800.0

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    // --- Новые тесты для граничных значений --- //

    @Test
    @DisplayName("Граница: ровно 1000 — скидка 0%")
    void shouldApplyZeroDiscountForAmountExactly1000() {
        double amount = 1000.0;
        double expectedPrice = 1000.0; // нет скидки

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Граница: 1000.01 — скидка 10%")
    void shouldApply10PercentDiscountForAmountJustAbove1000() {
        double amount = 1000.01;
        double expectedPrice = amount * 0.9; // скидка 10%

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Граница: ровно 5000 — скидка 10%")
    void shouldApply10PercentDiscountForAmountExactly5000() {
        double amount = 5000.0;
        double expectedPrice = amount * 0.9; // скидка 10%

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }

    @Test
    @DisplayName("Граница: 5000.01 — скидка 20%")
    void shouldApply20PercentDiscountForAmountJustAbove5000() {
        double amount = 5000.01;
        double expectedPrice = amount * 0.8; // скидка 20%

        double actualPrice = calculator.calculateDiscountedPrice(amount);

        assertThat(actualPrice).isEqualTo(expectedPrice, offset(0.01));
    }
}
