package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты корректности копирования (PerformanceComparison)")
public class PerformanceComparisonCorrectnessTest {

    @TempDir
    Path tempDir;

    private Path sourceFile;
    private Path destBufferedFile;
    private Path destUnbufferedFile;

    // Предполагается, что методы copyBuffered, copyUnbuffered и createLargeBinaryFile
    // либо вынесены отдельно в виде утилитных методов, либо сделаны общедоступными.

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        sourceFile = tempDir.resolve("source.bin");
        destBufferedFile = tempDir.resolve("dest_buffered.bin");
        destUnbufferedFile = tempDir.resolve("dest_unbuffered.bin");

        // Создадим небольшой тестовый файл (5 Кб)
        byte[] testData = new byte[1024 * 5]; // 5 KB
        for (int i = 0; i < testData.length; i++) {
            testData[i] = (byte) i;
        }
        Files.write(sourceFile, testData);
    }

    @Test
    @DisplayName("copyBuffered должен корректно копировать файл")
    void copyBufferedShouldCopyFileCorrectly() throws IOException {
        // Выполняем копирование методом copyBuffered
        PerformanceComparison.copyBuffered(sourceFile.toString(), destBufferedFile.toString());

        // Проверяем, что файл действительно существует и его содержимое такое же, как оригинал
        assertThat(Files.exists(destBufferedFile)).as("Буферизованный файл должен существовать").isTrue();
        assertThat(Files.mismatch(sourceFile, destBufferedFile)).as("Контент должен совпадать").isEqualTo(-1L);
    }

    @Test
    @DisplayName("copyUnbuffered должен корректно копировать файл")
    void copyUnbufferedShouldCopyFileCorrectly() throws IOException {
        // Выполняем копирование методом copyUnbuffered
        PerformanceComparison.copyUnbuffered(sourceFile.toString(), destUnbufferedFile.toString());

        // Проверяем, что файл действительно существует и его содержимое такое же, как оригинал
        assertThat(Files.exists(destUnbufferedFile)).as("Небуферизованный файл должен существовать").isTrue();
        assertThat(Files.mismatch(sourceFile, destUnbufferedFile)).as("Контент должен совпадать").isEqualTo(-1L);
    }

    @Test
    @DisplayName("createLargeBinaryFile должен создавать файл нужного размера")
    void createLargeBinaryFileShouldCreateFileOfCorrectSize() throws IOException {
        Path largeFile = tempDir.resolve("test_large.bin");
        long expectedSize = 1024 * 10; // 10 КБ для теста

        // Генерируем большой файл
        PerformanceComparison.createLargeBinaryFile(largeFile.toString(), expectedSize);

        // Проверяем существование файла и его размер
        assertThat(Files.exists(largeFile)).as("Большой файл должен существовать").isTrue();
        assertThat(Files.size(largeFile)).as("Размер файла должен соответствовать ожидаемому").isEqualTo(expectedSize);
    }
}
