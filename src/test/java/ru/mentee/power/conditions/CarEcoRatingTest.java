package ru.mentee.power.conditions;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

public class CarEcoRatingTest {

    private CarEcoRating ratingCalculator;
    private static final int ERROR = -1;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 100;

    @BeforeEach
    void setUp() {
        ratingCalculator = new CarEcoRating();
    }

    @Test
    @DisplayName("Расчет рейтинга для современного электромобиля")
    void calculateRatingForModernElectricCar() {
        // Arrange
        String fuelType = "Электро";
        double engineVolume = 0.0;
        double fuelConsumption = 15.0; // кВтч/100км
        int yearOfManufacture = 2023;
        boolean isEuroCompliant = false; // Не применимо

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        // Базовый рейтинг 90, штраф за расход 15*0.5=7.5, штраф за год 0.
        // Итого = 90 - 7.5 = 82.5 -> 82 (округление)
        int expectedRating = 82;
        assertThat(rating).isCloseTo(82, Offset.offset(1));
    }

    @Test
    @DisplayName("Расчет рейтинга для эффективного гибрида Евро-6")
    void calculateRatingForEfficientEuro6Hybrid() {
        // Arrange
        String fuelType = "Гибрид";
        double engineVolume = 1.5;
        double fuelConsumption = 4.0; // л/100км - меньше 5, бонус +15
        int yearOfManufacture = 2021;
        boolean isEuroCompliant = true; // Бонус +10

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        // База 70. Объем -7.5. Расход -8. Год 2021 (>2020) -1. Евро-6 +10. Расход<5 +15.
        // Итого = 70 - 7.5 - 8 - 1 + 10 + 15 = 78.5 -> 79 (округление)
        int expectedRating = 81;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Расчет рейтинга для нового дизельного автомобиля Евро-6")
    void calculateRatingForNewDieselCarEuro6() {
        // Arrange
        String fuelType = "Дизель";
        double engineVolume = 2.5;
        double fuelConsumption = 6.0;
        int yearOfManufacture = 2020;
        boolean isEuroCompliant = true;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        // База 40. Объем -12.5. Расход -10. Год 0. Евро +0.
        int expectedRating = 21;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Верхняя граница рейтинга (максимум 100)")
    void ensureMaximumRatingIs100() {
        // Arrange - создаем идеальный электромобиль будущего
        String fuelType = "Электро";
        double engineVolume = 0.0;
        double fuelConsumption = 0.1; // Почти нулевой расход
        int yearOfManufacture = Year.now().getValue(); // Текущий год
        boolean isEuroCompliant = true; // Предположим, что аргумент учитывается для электро

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        // Мы должны убедиться, что рейтинг не превышает максимально допустимый (100)
        assertThat(rating).isLessThanOrEqualTo(MAX_RATING);
    }

    @Test
    @DisplayName("Нижняя граница рейтинга (минимум 1)")
    void ensureMinimumRatingIs1() {
        // Arrange - создаем крайне неэффективный старый автомобиль
        String fuelType = "Бензин";
        double engineVolume = 7.0; // Огромный объем
        double fuelConsumption = 25.0; // Огромный расход
        int yearOfManufacture = 1980; // Очень старый
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        // База 30. Объем -35. Расход -50. Год (2020-1980=40) -40. Евро нет.
        // Итого = 30 - 35 - 50 - 40 = -95. Должен быть ограничен до MIN_RATING (1).
        int expectedRating = 1;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Обработка неизвестного типа топлива")
    void handleUnknownFuelType() {
        // Arrange
        String fuelType = "Водород"; // Неизвестный тип топлива
        double engineVolume = 0.0;
        double fuelConsumption = 10.0;
        int yearOfManufacture = 2020;
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        // При неизвестном типе топлива метод должен возвращать код ошибки
        int expectedRating = ERROR;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Обработка отрицательного объема двигателя")
    void handleNegativeEngineVolume() {
        // Arrange
        String fuelType = "Бензин";
        double engineVolume = -2.0; // Отрицательный объем двигателя
        double fuelConsumption = 10.0;
        int yearOfManufacture = 2020;
        boolean isEuroCompliant = false;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        int expectedRating = ERROR;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Обработка отрицательного расхода топлива")
    void handleNegativeFuelConsumption() {
        // Arrange
        String fuelType = "Дизель";
        double engineVolume = 2.0;
        double fuelConsumption = -5.0; // Отрицательный расход
        int yearOfManufacture = 2018;
        boolean isEuroCompliant = true;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        int expectedRating = ERROR;
        assertThat(rating).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("Обработка года выпуска в будущем")
    void handleFutureYearOfManufacture() {
        // Arrange
        String fuelType = "Гибрид";
        double engineVolume = 1.6;
        double fuelConsumption = 5.5;
        int yearOfManufacture = Year.now().getValue() + 5; // Год в будущем
        boolean isEuroCompliant = true;

        // Act
        int rating = ratingCalculator.calculateEcoRating(
                fuelType, engineVolume, fuelConsumption, yearOfManufacture, isEuroCompliant);

        // Assert
        int expectedRating = ERROR;
        assertThat(rating).isEqualTo(expectedRating);
    }
}
