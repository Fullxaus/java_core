package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("Тесты копирования файла (FileCopyDemo)")
public class FileCopyDemoTest {

    @TempDir
    Path tempDir;

    Path sourcePath;
    Path destinationPath;
    String sourceContent = "Строка 1 для копирования.\nLine 2 with English.\nИ русские буквы.";

    @BeforeEach
    void setUp() throws IOException {
        sourcePath = tempDir.resolve("source_test.txt");
        destinationPath = tempDir.resolve("dest_test.txt");
        Files.writeString(sourcePath, sourceContent, StandardCharsets.UTF_8);
        assertThat(sourcePath).exists();
    }

    private void performCopy(Path source, Path dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(source.toFile());
             FileOutputStream fos = new FileOutputStream(dest.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    @Test
    @DisplayName("Должен успешно копировать непустой файл")
    void shouldCopyNonEmptyFile() throws IOException {
        assertThat(destinationPath).doesNotExist();

        performCopy(sourcePath, destinationPath);

        assertThat(destinationPath).exists();
        String copiedContent = Files.readString(destinationPath, StandardCharsets.UTF_8);
        assertThat(copiedContent).isEqualTo(sourceContent);
    }

    @Test
    @DisplayName("Должен успешно копировать пустой файл")
    void shouldCopyEmptyFile() throws IOException {
        // Given
        Path emptySource = tempDir.resolve("empty_source.txt");
        Files.deleteIfExists(emptySource);
        Files.createFile(emptySource);
        // Проверяем, что исходный файл существует и его размер = 0
        assertThat(emptySource).exists();
        assertThat(Files.size(emptySource)).isZero();

        Path emptyDest = tempDir.resolve("empty_dest.txt");
        assertThat(emptyDest).doesNotExist();

        // When
        performCopy(emptySource, emptyDest);

        // Then
        assertThat(emptyDest).exists();
        assertThat(Files.size(emptyDest)).isZero();
    }

    @Test
    @DisplayName("Должен перезаписать существующий целевой файл")
    void shouldOverwriteExistingDestinationFile() throws IOException {
        // Given
        String initialDestContent = "Этот текст будет перезаписан.";
        Files.writeString(destinationPath, initialDestContent, StandardCharsets.UTF_8);
        assertThat(Files.readString(destinationPath, StandardCharsets.UTF_8))
                .isEqualTo(initialDestContent);

        // When
        performCopy(sourcePath, destinationPath);

        // Then
        assertThat(destinationPath).exists();
        String actual = Files.readString(destinationPath, StandardCharsets.UTF_8);
        assertThat(actual).isEqualTo(sourceContent);
    }

    @Test
    @DisplayName("Должен выбросить IOException, если исходный файл не существует")
    void shouldThrowIOExceptionIfSourceDoesNotExist() {
        Path nonExistentSource = tempDir.resolve("non_existent.txt");
        assertThat(nonExistentSource).doesNotExist();

        Throwable thrown = catchThrowable(() -> performCopy(nonExistentSource, destinationPath));

        assertThat(thrown).isInstanceOf(IOException.class);
        assertThat(destinationPath).doesNotExist();
    }
}

