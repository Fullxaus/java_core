package ru.mentee.power.loop;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class QuickSortAlgorithmTest {
    @Test
    void testQuickSort() {
        // Подготовка
        int[] testData = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};
        int[] expected = {1, 2, 3, 5, 5, 5, 6, 7, 8, 9};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(testData)).isEqualTo(expected);
    }

    @Test
    void testEmptyArray() {
        // Подготовка
        int[] emptyArray = {};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(emptyArray)).isEmpty();
    }

    @Test
    void testSingleElementArray() {
        // Подготовка
        int[] singleElementArray = {42};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(singleElementArray))
                .containsExactly(42);
    }

    @Test
    void testArrayWithNegativeValues() {
        // Подготовка
        int[] arrayWithNegatives = {-5, 7, -2, 9, -3, 5, -1, 8, 5, -6};
        int[] expected = {-6, -5, -3, -2, -1, 5, 5, 7, 8, 9};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(arrayWithNegatives))
                .isEqualTo(expected);
    }

    @Test
    void testAlreadySortedArray() {
        // Подготовка
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(sortedArray))
                .isEqualTo(sortedArray);
    }

    @Test
    void testReverseSortedArray() {
        // Подготовка
        int[] reverseSortedArray = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(reverseSortedArray))
                .isEqualTo(expected);
    }

    @Test
    void testArrayWithDuplicates() {
        // Подготовка
        int[] arrayWithDuplicates = {5, 5, 5, 5, 5};
        int[] expected = {5, 5, 5, 5, 5};

        // Проверка
        assertThat(QuickSortAlgorithm.quickSort(arrayWithDuplicates))
                .isEqualTo(expected);
    }

    @Test
    void testGenerateRandomArray() {
        // Подготовка
        int size = 100;
        int maxValue = 1000;

        // Генерация массива и проверка его размера
        int[] randomArray = QuickSortAlgorithm.generateRandomArray(size, maxValue);
        assertThat(randomArray).hasSize(size);

        // Проверка, что все элементы в диапазоне [0, maxValue]
        for (int value : randomArray) {
            assertThat(value).isBetween(0, maxValue);
        }
    }
}
