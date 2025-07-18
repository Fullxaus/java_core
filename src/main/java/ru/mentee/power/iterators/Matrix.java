package ru.mentee.power.iterators;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс, представляющий двумерную матрицу с различными способами итерации
 */
public class Matrix<T> implements Iterable<T> {
    private final T[][] data;
    private final int rows;
    private final int columns;

    @SuppressWarnings("unchecked")
    public Matrix(T[][] data) {
        if (data == null) {
            throw new IllegalArgumentException("Массив данных не может быть null");
        }

        this.rows = data.length;
        if (rows == 0) {
            this.columns = 0;
        } else {
            this.columns = data[0].length;
            for (T[] row : data) {
                if (row.length != columns) {
                    throw new IllegalArgumentException("Все строки матрицы должны иметь одинаковую длину");
                }
            }
        }

        this.data = (T[][]) new Object[rows][];
        for (int i = 0; i < rows; i++) {
            this.data[i] = (T[]) new Object[columns];
            System.arraycopy(data[i], 0, this.data[i], 0, columns);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public T get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
        }
        return data[row][column];
    }

    public void set(int row, int column, T value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
        }
        data[row][column] = value;
    }

    @Override
    public Iterator<T> iterator() {
        return new RowMajorIterator();
    }

    public Iterator<T> rowMajorIterator() {
        return new RowMajorIterator();
    }

    public Iterator<T> columnMajorIterator() {
        return new ColumnMajorIterator();
    }

    public Iterator<T> spiralIterator() {
        return new SpiralIterator();
    }

    public Iterator<T> zigzagIterator() {
        return new ZigzagIterator();
    }

    // 1) Построчный итератор
    private class RowMajorIterator implements Iterator<T> {
        private int currentRow = 0;
        private int currentColumn = 0;

        @Override
        public boolean hasNext() {
            return currentRow < rows && currentColumn < columns;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T element = data[currentRow][currentColumn];
            currentColumn++;
            if (currentColumn >= columns) {
                currentColumn = 0;
                currentRow++;
            }
            return element;
        }
    }

    // 2) Итератор по столбцам
    private class ColumnMajorIterator implements Iterator<T> {
        private int currentRow = 0;
        private int currentColumn = 0;

        @Override
        public boolean hasNext() {
            return currentColumn < columns && currentRow < rows;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T element = data[currentRow][currentColumn];
            currentRow++;
            if (currentRow >= rows) {
                currentRow = 0;
                currentColumn++;
            }
            return element;
        }
    }

    // 3) Итератор по спирали от центра к краям
    private class SpiralIterator implements Iterator<T> {
        private final int total = rows * columns;
        private int count = 0;

        // старт из центральной ячейки
        private int row = (rows - 1) / 2;
        private int col = (columns - 1) / 2;

        // направления: вправо, вниз, влево, вверх
        private final int[] dRow = {0, 1, 0, -1};
        private final int[] dCol = {1, 0, -1, 0};
        private int dir = 0;      // текущее направление в dRow/dCol
        private int step = 1;     // сколько шагов сделать в текущем направлении
        private int stepCount = 0;     // сколько шагов уже сделано
        private int changeDirCount = 0; // после двух прогонов step увеличивается

        @Override
        public boolean hasNext() {
            return count < total;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T result = null;
            // Продолжаем двигаться, пока не попадём в валидную ячейку
            while (count < total && result == null) {
                if (row >= 0 && row < rows && col >= 0 && col < columns) {
                    result = data[row][col];
                    count++;
                    if (count == total) break;
                }

                // делаем шаг
                row += dRow[dir];
                col += dCol[dir];
                stepCount++;
                if (stepCount == step) {
                    stepCount = 0;
                    dir = (dir + 1) % 4;
                    changeDirCount++;
                    if (changeDirCount % 2 == 0) {
                        step++;
                    }
                }
            }
            return result;
        }
    }

    // 4) Итератор «змейкой»
    private class ZigzagIterator implements Iterator<T> {
        private int currentRow = 0;
        private int currentColumn = 0;
        private boolean leftToRight = true;

        @Override
        public boolean hasNext() {
            return currentRow < rows;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T element = data[currentRow][currentColumn];

            // переход
            if (leftToRight) {
                currentColumn++;
                if (currentColumn >= columns) {
                    currentRow++;
                    currentColumn = columns - 1;
                    leftToRight = false;
                }
            } else {
                currentColumn--;
                if (currentColumn < 0) {
                    currentRow++;
                    currentColumn = 0;
                    leftToRight = true;
                }
            }

            return element;
        }
    }
}

