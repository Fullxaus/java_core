package ru.mentee.power.conditions;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WeekdayDeterminerTest {

    @Test
    void testGetDayDescription_Monday() {
        assertThat(WeekdayDeterminer.getDayDescription(1))
                .isEqualTo("Понедельник - рабочий день\nТяжелый день");
    }

    @Test
    void testGetDayDescription_Wednesday() {
        assertThat(WeekdayDeterminer.getDayDescription(3))
                .isEqualTo("Среда - рабочий день\nСередина недели");
    }

    @Test
    void testGetDayDescription_Friday() {
        assertThat(WeekdayDeterminer.getDayDescription(5))
                .isEqualTo("Пятница - рабочий день\nСкоро выходные!");
    }

    @Test
    void testGetDayDescription_Saturday() {
        assertThat(WeekdayDeterminer.getDayDescription(6))
                .isEqualTo("Суббота - выходной");
    }

    @Test
    void testGetDayDescription_Sunday() {
        assertThat(WeekdayDeterminer.getDayDescription(7))
                .isEqualTo("Воскресенье - выходной");
    }



    @Test
    void testGetDayDescription_InvalidDay() {
        // Используя assertThat, проверьте, что для некорректных дней (например, 0, 8)
        // метод возвращает "Некорректный день недели".
        assertThat(WeekdayDeterminer.getDayDescription(0)).isEqualTo("Некорректный день недели");
        assertThat(WeekdayDeterminer.getDayDescription(8)).isEqualTo("Некорректный день недели");
        assertThat(WeekdayDeterminer.getDayDescription(-5)).isEqualTo("Некорректный день недели");
        assertThat(WeekdayDeterminer.getDayDescription(100)).isEqualTo("Некорректный день недели");
        assertThat(WeekdayDeterminer.getDayDescription(-100)).isEqualTo("Некорректный день недели");

    }
}
