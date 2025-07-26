package ru.mentee.power.conditions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Юнит-тесты для класса {@link RentCar}.
 */
public class CarRentalTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        outputStreamCaptor.reset();
    }

    @Test
    @DisplayName("rentCarWithIf: возраст 18 — можно арендовать")
    public void testRentCarWithIfEligible() {
        RentCar.setUserAge(18);
        RentCar.rentCarWithIf();
        String expected = "Вы можете арендовать автомобиль." + System.lineSeparator();
        assertEquals(expected, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName("rentCarWithIf: возраст 17 — нельзя арендовать")
    public void testRentCarWithIfNotEligible() {
        RentCar.setUserAge(17);
        RentCar.rentCarWithIf();
        String expected = "Вы не можете арендовать автомобиль." + System.lineSeparator();
        assertEquals(expected, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName("rentCarWithTernary: возраст 18 — можно арендовать")
    public void testRentCarWithTernaryEligible() {
        RentCar.setUserAge(18);
        RentCar.rentCarWithTernary();
        String expected = "Вы можете арендовать автомобиль." + System.lineSeparator();
        assertEquals(expected, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName("rentCarWithTernary: возраст 17 — нельзя арендовать")
    public void testRentCarWithTernaryNotEligible() {
        RentCar.setUserAge(17);
        RentCar.rentCarWithTernary();
        String expected = "Вы не можете арендовать автомобиль." + System.lineSeparator();
        assertEquals(expected, outputStreamCaptor.toString());
    }
}