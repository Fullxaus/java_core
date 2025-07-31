package ru.mentee.power.loop;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuickSortAlgorithm {

    // Логгер для вывода сообщений
    private static final Logger logger =
            Logger.getLogger(QuickSortAlgorithm.class.getName());

    // Общий экземпляр Random для всего класса (исправлено: не создавать новый Random в каждом вызове)
    private static final Random RANDOM = new Random();

    /**
     * Сортирует массив с помощью алгоритма быстрой сортировки.
     *
     * @param array массив для сортировки
     * @return отсортированная копия массива
     */
    public static int[] quickSort(int[] array) {
        int[] result = Arrays.copyOf(array, array.length);
        quickSortRecursive(result, 0, result.length - 1);
        return result;
    }

    private static void quickSortRecursive(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSortRecursive(array, low, pivotIndex - 1);
            quickSortRecursive(array, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    /**
     * Генерирует случайный массив целых чисел заданного размера.
     * Использует общий экземпляр RANDOM для всей программы.
     *
     * @param size размер массива
     * @param maxValue максимальное значение элемента
     * @return случайный массив
     */
    public static int[] generateRandomArray(int size, int maxValue) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = RANDOM.nextInt(maxValue + 1);
        }
        return result;
    }

    public static long measureSortingTime(int[] array) {
        long start = System.currentTimeMillis();
        quickSort(array);
        return System.currentTimeMillis() - start;
    }

    public static void compareWithJavaSort(int[] array) {
        int[] a1 = Arrays.copyOf(array, array.length);
        int[] a2 = Arrays.copyOf(array, array.length);

        long t1 = System.currentTimeMillis();
        quickSort(a1);
        long t2 = System.currentTimeMillis();

        long t3 = System.currentTimeMillis();
        Arrays.sort(a2);
        long t4 = System.currentTimeMillis();

        final int len = array.length;
        logger.log(Level.INFO, () -> "Сравнение производительности на массиве размером " + len);
        logger.log(Level.INFO, () -> "Наша реализация Quick Sort: " + (t2 - t1) + " мс");
        logger.log(Level.INFO, () -> "Java Arrays.sort():         " + (t4 - t3) + " мс");
    }

    public static void main(String[] args) {
        int[] testArray = {5, 7, 2, 9, 3, 5, 1, 8, 5, 6};

        logger.log(Level.INFO, () -> "Исходный массив: " + Arrays.toString(testArray));
        logger.log(Level.INFO, () -> "Отсортированный массив: " +
                Arrays.toString(quickSort(testArray)));

        int[] largeArray = generateRandomArray(100_000, 1_000);
        compareWithJavaSort(largeArray);
    }
}
