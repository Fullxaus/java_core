package ru.mentee.power.loop;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayStatisticsTest {

    @Test
    void testFindMinMax() {
        int[] testData = {5,7,2,9,3,5,1,8,5,6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        assertThat(stats.findMin()).isEqualTo(1);
        assertThat(stats.findMax()).isEqualTo(9);
    }

    @Test
    void testCalculateSumAndAverage() {
        int[] testData = {5,7,2,9,3,5,1,8,5,6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        assertThat(stats.calculateSum()).isEqualTo(51);
        // 51/10 = 5.1
        assertThat(stats.calculateAverage()).isEqualTo(5.1);
    }

    @Test
    void testCalculateMedian() {
        // Чётное число элементов (10)
        int[] even = {5,7,2,9,3,5,1,8,5,6};
        // Нечётное число элементов (9)
        int[] odd  = {5,7,2,9,3,5,1,8,5};

        ArrayStatistics sEven = new ArrayStatistics(even);
        ArrayStatistics sOdd  = new ArrayStatistics(odd);

        // Отсортированные even: 1,2,3,5,5,5,6,7,8,9 → среднее 5 и 5 → 5.0
        assertThat(sEven.calculateMedian()).isEqualTo(5.0);

        // Отсортированные odd: 1,2,3,5,5,5,7,8,9 → центральный элемент 5
        assertThat(sOdd.calculateMedian()).isEqualTo(5.0);
    }

    @Test
    void testFindMode() {
        int[] testData = {5,7,2,9,3,5,1,8,5,6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // 5 встречается 3 раза, другие — меньше
        assertThat(stats.findMode()).isEqualTo(5);
    }

    @Test
    void testCalculateStandardDeviation() {
        int[] data = {5,7,2,9,3,5,1,8,5,6};
        ArrayStatistics stats = new ArrayStatistics(data);

        // Ручной расчёт даёт примерно 2.510...
        double stdDev = stats.calculateStandardDeviation();
        // Округляем до 2 знаков после запятой
        double rounded = Math.round(stdDev * 100) / 100.0;
        assertThat(rounded).isEqualTo(2.43);
    }

    @Test
    void testCountGreaterAndLessThan() {
        int[] testData = {5,7,2,9,3,5,1,8,5,6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        // >5 : 7,9,8,6 → 4 штуки
        assertThat(stats.countGreaterThan(5)).isEqualTo(4);
        // <5 : 2,3,1 → 3 штуки
        assertThat(stats.countLessThan(5)).isEqualTo(3);
    }

    @Test
    void testContains() {
        int[] testData = {5,7,2,9,3,5,1,8,5,6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        assertThat(stats.contains(7)).isTrue();
        assertThat(stats.contains(10)).isFalse();
    }

    @Test
    void testEmptyArray() {
        int[] empty = {};
        ArrayStatistics stats = new ArrayStatistics(empty);

        assertThat(stats.findMin()).isEqualTo(Integer.MAX_VALUE);
        assertThat(stats.findMax()).isEqualTo(Integer.MIN_VALUE);
        assertThat(stats.calculateSum()).isEqualTo(0);
        assertThat(stats.calculateAverage()).isEqualTo(0.0);
        assertThat(stats.calculateMedian()).isEqualTo(0.0);
        assertThat(stats.findMode()).isEqualTo(0);
        assertThat(stats.calculateStandardDeviation()).isEqualTo(0.0);
        assertThat(stats.countGreaterThan(0)).isEqualTo(0);
        assertThat(stats.countLessThan(0)).isEqualTo(0);
        assertThat(stats.contains(5)).isFalse();
    }

    @Test
    void testSingleElementArray() {
        int[] single = {42};
        ArrayStatistics stats = new ArrayStatistics(single);

        assertThat(stats.findMin()).isEqualTo(42);
        assertThat(stats.findMax()).isEqualTo(42);
        assertThat(stats.calculateSum()).isEqualTo(42);
        assertThat(stats.calculateAverage()).isEqualTo(42.0);
        assertThat(stats.calculateMedian()).isEqualTo(42.0);
        assertThat(stats.findMode()).isEqualTo(42);
        // при одном элементе дисперсия = 0
        assertThat(stats.calculateStandardDeviation()).isEqualTo(0.0);
        assertThat(stats.countGreaterThan(42)).isEqualTo(0);
        assertThat(stats.countLessThan(42)).isEqualTo(0);
        assertThat(stats.contains(42)).isTrue();
    }

    @Test
    void testArrayWithAllDuplicates() {
        int[] dup = {7,7,7,7};
        ArrayStatistics stats = new ArrayStatistics(dup);

        assertThat(stats.findMin()).isEqualTo(7);
        assertThat(stats.findMax()).isEqualTo(7);
        assertThat(stats.calculateSum()).isEqualTo(28);
        assertThat(stats.calculateAverage()).isEqualTo(7.0);
        assertThat(stats.calculateMedian()).isEqualTo(7.0);
        assertThat(stats.findMode()).isEqualTo(7);
        assertThat(stats.calculateStandardDeviation()).isEqualTo(0.0);
        assertThat(stats.countGreaterThan(7)).isEqualTo(0);
        assertThat(stats.countLessThan(7)).isEqualTo(0);
        assertThat(stats.contains(7)).isTrue();
    }

    @Test
    void testArrayWithNegativeValues() {
        int[] neg = {-3, -1, -4, -1, -5};
        ArrayStatistics stats = new ArrayStatistics(neg);

        assertThat(stats.findMin()).isEqualTo(-5);
        assertThat(stats.findMax()).isEqualTo(-1);
        assertThat(stats.calculateSum()).isEqualTo(-14);
        assertThat(stats.calculateAverage()).isEqualTo(-2.8);
        // отсортировано: -5, -4, -3, -1, -1 → медиана = -3
        assertThat(stats.calculateMedian()).isEqualTo(-3.0);
        // -1 встречается 2 раза, остальные по 1 → мода = -1
        assertThat(stats.findMode()).isEqualTo(-1);
        // Стд отклонение можно проверить на порядок, например:
        double sd = stats.calculateStandardDeviation();
        double rounded = Math.round(sd * 100) / 100.0;
        // ручной расчёт: √[(2.44+3.24+0.04+3.24+5.44)/5] ≈ √(14.4/5)=√2.88≈1.697
        assertThat(rounded).isEqualTo(1.60);
        assertThat(stats.countGreaterThan(-3)).isEqualTo(2);  // -1, -1
        assertThat(stats.countLessThan(-3)).isEqualTo(2);     // -5, -4
        assertThat(stats.contains(-1)).isTrue();
        assertThat(stats.contains(0)).isFalse();
    }

}
