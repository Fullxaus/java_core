package ru.mentee.power.loop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Класс для вычисления и логирования статистических показателей массива.
 */
public class ArrayStatistics {
    // Логгер для вывода сообщений
    private static final Logger logger = Logger.getLogger(ArrayStatistics.class.getName());

    // Копия входного массива для безопасного анализа
    private final int[] data;

    public ArrayStatistics(int[] data) {
        this.data = Arrays.copyOf(data, data.length);
    }

    /**
     * @return минимальное значение или Integer.MAX_VALUE, если пустой
     */
    public int findMin() {
        if (data.length == 0) {
            return Integer.MAX_VALUE;
        }
        int min = data[0];
        for (int v : data) {
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    /**
     * @return максимальное значение или Integer.MIN_VALUE, если пустой
     */
    public int findMax() {
        if (data.length == 0) {
            return Integer.MIN_VALUE;
        }
        int max = data[0];
        for (int v : data) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    /**
     * @return сумма всех элементов (0 для пустого)
     */
    public int calculateSum() {
        int sum = 0;
        for (int v : data) {
            sum += v;
        }
        return sum;
    }

    /**
     * @return среднее арифметическое или 0.0, если пустой
     */
    public double calculateAverage() {
        if (data.length == 0) {
            return 0.0;
        }
        return (double) calculateSum() / data.length;
    }

    /**
     * @return медиана или 0.0, если пустой
     */
    public double calculateMedian() {
        int n = data.length;
        if (n == 0) {
            return 0.0;
        }
        int[] copy = Arrays.copyOf(data, n);
        Arrays.sort(copy);
        if (n % 2 == 1) {
            return copy[n / 2];
        } else {
            int mid1 = copy[n / 2 - 1];
            int mid2 = copy[n / 2];
            return (mid1 + mid2) / 2.0;
        }
    }

    /**
     * @return мода (наименьшее из наиболее частых) или 0, если пустой
     */
    public int findMode() {
        if (data.length == 0) {
            return 0;
        }
        Map<Integer, Integer> freq = new HashMap<>();
        for (int v : data) {
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }
        int mode = data[0];
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int value = e.getKey();
            int count = e.getValue();
            if (count > maxCount || (count == maxCount && value < mode)) {
                maxCount = count;
                mode = value;
            }
        }
        return mode;
    }

    /**
     * @return стандартное отклонение (популяционное) или 0.0, если элементов < 2
     */
    public double calculateStandardDeviation() {
        int n = data.length;
        if (n < 2) {
            return 0.0;
        }
        double mean = calculateAverage();
        double sumSquaredDiffs = 0.0;
        for (int v : data) {
            double diff = v - mean;
            sumSquaredDiffs += diff * diff;
        }
        double variance = sumSquaredDiffs / n;
        return Math.sqrt(variance);
    }

    /**
     * @param value порог
     * @return кол-во элементов > value
     */
    public int countGreaterThan(int value) {
        int count = 0;
        for (int v : data) {
            if (v > value) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param value порог
     * @return кол-во элементов < value
     */
    public int countLessThan(int value) {
        int count = 0;
        for (int v : data) {
            if (v < value) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param value искомое значение
     * @return true, если есть хотя бы одно совпадение
     */
    public boolean contains(int value) {
        for (int v : data) {
            if (v == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Выводит в лог все основные статистические показатели
     */
    public void printStatisticsReport() {
        logger.info("===== Статистический отчет =====");
        logger.info("Размер массива: " + data.length);
        logger.info("Минимальное значение: " + findMin());
        logger.info("Максимальное значение: " + findMax());
        logger.info("Сумма элементов: " + calculateSum());
        logger.info("Среднее арифметическое: " + calculateAverage());
        logger.info("Медиана: " + calculateMedian());
        logger.info("Мода: " + findMode());
        logger.info("Стандартное отклонение: " + calculateStandardDeviation());
        logger.info("================================");
    }

    public static void main(String[] args) {
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        ArrayStatistics stats = new ArrayStatistics(testData);

        logger.info("Исходный массив: " + Arrays.toString(testData));
        stats.printStatisticsReport();
        logger.info("Элементов > 5: " + stats.countGreaterThan(5));
        logger.info("Элементов < 5: " + stats.countLessThan(5));
        logger.info("Массив содержит 7: " + stats.contains(7));
        logger.info("Массив содержит 10: " + stats.contains(10));
    }
}