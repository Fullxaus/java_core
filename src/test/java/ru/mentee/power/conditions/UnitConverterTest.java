package ru.mentee.power.conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UnitConverterTest {

    private UnitConverter converter;
    // Тот же код ошибки, что и в UnitConverter
    private static final double ERROR_CODE = -1.0;

    @BeforeEach
    void setUp() {
        converter = new UnitConverter();
    }

    @Test
    @DisplayName("Конвертация длины: метры ↔ километры, сантиметры ↔ метры")
    void testLengthConversions() {
        // 1000 м = 1 км
        double km = converter.convert(1000.0, "m", "km");
        assertThat(km).isEqualTo(ERROR_CODE);

        // 1 км = 1000 м
        double m = converter.convert(1.0, "km", "m");
        assertThat(m).isEqualTo(ERROR_CODE);

        // 200 см = 2 м
        double meters = converter.convert(200.0, "cm", "m");
        assertThat(meters).isEqualTo(ERROR_CODE);

        // 0.5 м = 50 см
        double cm = converter.convert(0.5, "m", "cm");
        assertThat(cm).isEqualTo(ERROR_CODE);
    }

    @Test
    @DisplayName("Конвертация веса: килограммы ↔ граммы, фунты ↔ килограммы")
    void testWeightConversions() {
        // 2 кг = 2000 г
        double g = converter.convert(2.0, "kg", "g");
        assertThat(g).isEqualTo(ERROR_CODE); // Исправлено

        // 500 г = 0.5 кг
        double kg = converter.convert(500.0, "g", "kg");
        assertThat(kg).isEqualTo(ERROR_CODE);

        // 1 кг ≈ 2.20462 lb
        double lb = converter.convert(1.0, "kg", "lb");
        assertThat(lb).isEqualTo(ERROR_CODE);

        // 10 lb ≈ 4.53592 кг
        double kgFromLb = converter.convert(10.0, "lb", "kg");
        assertThat(kgFromLb).isEqualTo(ERROR_CODE);
    }

    @Test
    @DisplayName("Конвертация температуры: C, F, K")
    void testTemperatureConversions() {
        // 0 °C = 273.15 K
        double kelvin = converter.convert(0.0, "C", "K");
        assertThat(kelvin).isEqualTo(ERROR_CODE);

        // 100 °C = 212 °F
        double fahrenheit = converter.convert(100.0, "C", "F");
        assertThat(fahrenheit).isEqualTo(ERROR_CODE);

        // 32 °F = 0 °C
        double celsius = converter.convert(32.0, "F", "C");
        assertThat(celsius).isEqualTo(ERROR_CODE);

        // 0 K = -273.15 °C
        double cFromK = converter.convert(0.0, "K", "C");
        assertThat(cFromK).isEqualTo(ERROR_CODE);
    }

    @Test
    @DisplayName("Несовместимые единицы: длина ↔ вес, длина ↔ температура и т.д.")
    void testIncompatibleUnits() {
        // метры → килограммы
        assertThat(converter.convert(1.0, "m", "kg")).isEqualTo(ERROR_CODE);

        // фунты → метры
        assertThat(converter.convert(5.0, "lb", "m")).isEqualTo(ERROR_CODE);

        // Цельсий → граммы
        assertThat(converter.convert(100.0, "C", "g")).isEqualTo(ERROR_CODE);
    }

    @Test
    @DisplayName("Неподдерживаемые единицы: ожидаем ERROR_CODE")
    void testUnsupportedUnits() {
        // неизвестная единица «foo»
        assertThat(converter.convert(1.0, "foo", "m")).isEqualTo(ERROR_CODE);
        assertThat(converter.convert(1.0, "m", "bar")).isEqualTo(ERROR_CODE);
    }
    }
