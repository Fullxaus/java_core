package ru.mentee.power.nio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.NoSuchFileException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тесты утилиты копирования NioFileUtil")
public class NioFileUtilTest {

    @TempDir
    Path tempDir;

    private Path sourceFilePath;
    private Path destinationFilePath;
    private String testContent = "Тестовое содержимое для NioFileUtil";

    @BeforeEach
    void setUp() throws IOException {
        sourceFilePath = tempDir.resolve("source_util_test.txt");
        destinationFilePath = tempDir.resolve("destination_util_test.txt");
        // Создаем исходный файл
        Files.writeString(sourceFilePath, testContent, StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("copyFile должен успешно копировать существующий файл")
    void copyFileShouldCopyExistingFile() throws IOException {
        // Given (в setUp)
        assertThat(destinationFilePath).doesNotExist();

        // When
        NioFileUtil.copyFile(sourceFilePath, destinationFilePath);

        // Then
        assertThat(destinationFilePath).exists();
        assertThat(Files.readString(destinationFilePath, StandardCharsets.UTF_8)).isEqualTo(testContent);
    }

    @Test
    @DisplayName("copyFile должен перезаписать существующий целевой файл")
    void copyFileShouldOverwriteExistingDestinationFile() throws IOException {
        // Given: Целевой файл уже существует с другим содержимым
        String initialDestContent = "Старое содержимое";
        Files.writeString(destinationFilePath, initialDestContent, StandardCharsets.UTF_8);
        assertThat(destinationFilePath).hasContent(initialDestContent);

        // When
        NioFileUtil.copyFile(sourceFilePath, destinationFilePath);

        // Then
        assertThat(destinationFilePath).exists();
        assertThat(Files.readString(destinationFilePath, StandardCharsets.UTF_8)).isEqualTo(testContent);
    }

    @Test
    @DisplayName("copyFile должен выбросить NoSuchFileException, если исходный файл не существует")
    void copyFileShouldThrowExceptionIfSourceDoesNotExist() throws IOException {
        // Given: Исходный файл удален
        Files.delete(sourceFilePath);
        assertThat(sourceFilePath).doesNotExist();

        // When & Then
        assertThatThrownBy(() -> NioFileUtil.copyFile(sourceFilePath, destinationFilePath))
                .isInstanceOf(NoSuchFileException.class);

        // Убедиться, что целевой файл не был создан
        assertThat(destinationFilePath).doesNotExist();
    }
}

