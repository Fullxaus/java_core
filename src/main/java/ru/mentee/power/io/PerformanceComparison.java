package ru.mentee.power.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PerformanceComparison {

    private static final String SOURCE_FILE = "large_test_file.bin";
    private static final String DEST_BUFFERED = "copy_buffered.bin";
    private static final String DEST_UNBUFFERED = "copy_unbuffered.bin";
    private static final long FILE_SIZE_MB = 20; // Размер файла в мегабайтах
    private static final long FILE_SIZE_BYTES = FILE_SIZE_MB * 1024 * 1024;
    private static final int BUFFER_SIZE = 8192; // Размер буфера для буферизованного копирования

    public static void main(String[] args) {
        try {
            createLargeBinaryFile(SOURCE_FILE, FILE_SIZE_BYTES);

            System.out.println("Копируем файл размера " + FILE_SIZE_MB + "MB...\n");

            // Буферизованное копирование
            long startTimeBuffered = System.nanoTime();
            copyBuffered(SOURCE_FILE, DEST_BUFFERED);
            long endTimeBuffered = System.nanoTime();
            long durationBuffered = (endTimeBuffered - startTimeBuffered) / 1_000_000; // Преобразование в миллисекунды
            System.out.println("Буферизованное копирование: " + durationBuffered + "ms");

            // Небуферизованное копирование
            long startTimeUnbuffered = System.nanoTime();
            copyUnbuffered(SOURCE_FILE, DEST_UNBUFFERED);
            long endTimeUnbuffered = System.nanoTime();
            long durationUnbuffered = (endTimeUnbuffered - startTimeUnbuffered) / 1_000_000; // Преобразование в миллисекунды
            System.out.println("Небуферизованное копирование: " + durationUnbuffered + "ms");

            // Проверка корректности копии
            System.out.println("\nПроверка корректности копий...");
            Path sourcePath = Paths.get(SOURCE_FILE);
            Path bufferedPath = Paths.get(DEST_BUFFERED);
            Path unbufferedPath = Paths.get(DEST_UNBUFFERED);

            boolean matchBuffered = checkFileEquality(sourcePath, bufferedPath);
            boolean matchUnbuffered = checkFileEquality(sourcePath, unbufferedPath);

            System.out.println("Буферизованная копия совпадает: " + matchBuffered);
            System.out.println("Небуферизованная копия совпадает: " + matchUnbuffered);

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanUp(SOURCE_FILE, DEST_BUFFERED, DEST_UNBUFFERED);
        }
    }

    /**
     * Копирует файл с использованием буферизованных потоков.
     */
     static void copyBuffered(String src, String dst) throws IOException {
        try (
                InputStream in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                OutputStream out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE)
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
    }

    /**
     * Копирует файл без буферизации (чтение-перемещение по одному байту).
     */
     static void copyUnbuffered(String src, String dst) throws IOException {
        try (
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst)
        ) {
            int byteRead;
            while ((byteRead = in.read()) != -1) {
                out.write(byteRead);
            }
        }
    }

    /**
     * Генерирует большой бинарный файл.
     */
     static void createLargeBinaryFile(String filename, long sizeBytes) throws IOException {
        try (OutputStream os = new FileOutputStream(filename)) {
            byte[] chunk = new byte[1024]; // Используем чанк фиксированного размера
            for (long i = 0; i < sizeBytes; i += chunk.length) {
                int remaining = (int) Math.min(chunk.length, sizeBytes - i);
                os.write(chunk, 0, remaining);
            }
        }
    }

    /**
     * Проверяет равенство двух файлов.
     */
     static boolean checkFileEquality(Path path1, Path path2) throws IOException {
        return Files.mismatch(path1, path2) == -1L;
    }

    /**
     * Удаляет указанные файлы.
     */
     static void cleanUp(String... files) {
        for (String file : files) {
            try {
                Files.deleteIfExists(Paths.get(file));
            } catch (IOException ignored) {}
        }
    }
}
