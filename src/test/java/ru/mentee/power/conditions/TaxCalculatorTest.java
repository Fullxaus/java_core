package ru.mentee.power.conditions;

import org.junit.jupiter.api.Test;
// Используем статический импорт для assertThat и offset
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class TaxCalculatorTest {

    public static final double DELTA = 0.01; // Погрешность для сравнения double

    @Test
    void testCalculateTax_Tier1() {
        assertThat(TaxCalculator.calculateTax(80000.0)).isCloseTo(8000.0, offset(DELTA)); // 10%
        assertThat(TaxCalculator.calculateTax(100000.0)).isCloseTo(10000.0, offset(DELTA)); // 10%
    }

    @Test
    void testCalculateTax_Tier2() {
        assertThat(TaxCalculator.calculateTax(200000.0)).isCloseTo(30000.0, offset(DELTA)); // 15%
        assertThat(TaxCalculator.calculateTax(500000.0)).isCloseTo(75000.0, offset(DELTA)); // 15%
    }

    @Test
    void testCalculateTax_Tier3() {
        assertThat(TaxCalculator.calculateTax(750000.0)).isCloseTo(150000.0, offset(DELTA)); // 20%
        assertThat(TaxCalculator.calculateTax(1000000.0)).isCloseTo(200000.0, offset(DELTA)); // 20%
    }

    @Test
    void testCalculateTax_Tier4() {
        assertThat(TaxCalculator.calculateTax(1500000.0)).isCloseTo(375000.0, offset(DELTA)); // 25%
    }

    @Test
    void testCalculateTax_ZeroIncome() {
        assertThat(TaxCalculator.calculateTax(0.0)).isCloseTo(0.0, offset(DELTA));
    }
    @Test
    void testCalculateTax_NegativeIncome() {
        // Используя assertThat, проверьте, что для отрицательного дохода метод возвращает -1
        assertThat(TaxCalculator.calculateTax(-10000.0)).isCloseTo(-1.0, offset(DELTA));
        assertThat(TaxCalculator.calculateTax(-0.01)).isCloseTo(-1.0, offset(DELTA));
        assertThat(TaxCalculator.calculateTax(-100)).isCloseTo(-1.0, offset(DELTA));
        assertThat(TaxCalculator.calculateTax(-0.001)).isCloseTo(-1.0, offset(DELTA));
        assertThat(TaxCalculator.calculateTax(Double.NEGATIVE_INFINITY)).isCloseTo(-1.0, offset(DELTA));
    }
}
