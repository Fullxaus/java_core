package ru.mentee.power.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты просмотрщика директорий (DirectoryViewer)")
public class DirectoryViewerTest {

    @TempDir
    Path tempDir; // Временная директория для тестов

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Вспомогательный метод для нормализации вывода (\r\n -> \n)
    private String normalize(String s) {
        return s.replace("\r\n", "\n");
    }

    @Test
    @DisplayName("Должен вывести содержимое непустой директории")
    void shouldListNonEmptyDirectoryContents() throws IOException {
        // Given: создаем файлы и поддиректорию во временной папке
        Path textFile = tempDir.resolve("file1.txt");
        Files.writeString(textFile, "content1");      // 8 байт
        Path imageFile = tempDir.resolve("image.jpg");
        Files.createFile(imageFile);                  // 0 байт
        Path subDirectory = tempDir.resolve("subdir");
        Files.createDirectory(subDirectory);

        // When: запускаем main
        DirectoryViewer.main(new String[]{tempDir.toString()});

        // Then: проверяем вывод
        String out = normalize(outContent.toString());
        String err = normalize(errContent.toString());

        // Абсолютный путь
        assertThat(out).contains("Содержимое директории: " + tempDir.toAbsolutePath());
        // Проверяем, что упомянуты все три элемента (с одним пробелом для файлов и двумя для директории)
        assertThat(out).contains("[FILE] file1.txt (8 bytes)");
        assertThat(out).contains("[FILE] image.jpg (0 bytes)");
        assertThat(out).contains("[DIR]  subdir");  // <-- здесь два пробела
        // Нет сообщений в err
        assertThat(err).isEmpty();
    }
    @Test
    @DisplayName("Должен вывести сообщение для пустой директории")
    void shouldHandleEmptyDirectory() {
        // Given: tempDir пустой

        // When
        DirectoryViewer.main(new String[]{tempDir.toString()});

        // Then
        String out = normalize(outContent.toString());
        String err = normalize(errContent.toString());

        assertThat(out)
                .contains("Содержимое директории: " + tempDir.toAbsolutePath())
                .contains("Директория пуста.");
        assertThat(err).isEmpty();
    }

    @Test
    @DisplayName("Должен вывести ошибку, если путь не существует")
    void shouldPrintErrorForNonExistentPath() {
        // Given
        Path nonExistent = tempDir.resolve("no_such");
        assertThat(nonExistent).doesNotExist();

        // When
        DirectoryViewer.main(new String[]{nonExistent.toString()});

        // Then
        String out = normalize(outContent.toString());
        String err = normalize(errContent.toString());

        assertThat(out).isEmpty();
        assertThat(err)
                .contains("Ошибка: Путь не существует или не является директорией: " + nonExistent.toString());
    }

    @Test
    @DisplayName("Должен вывести ошибку, если путь указывает на файл")
    void shouldPrintErrorForFilePath() throws IOException {
        // Given
        Path filePath = tempDir.resolve("my_file.txt");
        Files.createFile(filePath);
        assertThat(filePath).isRegularFile();

        // When
        DirectoryViewer.main(new String[]{filePath.toString()});

        // Then
        String out = normalize(outContent.toString());
        String err = normalize(errContent.toString());

        assertThat(out).isEmpty();
        assertThat(err)
                .contains("Ошибка: Путь не существует или не является директорией: " + filePath.toString());
    }

    @Test
    @DisplayName("Должен вывести ошибку, если аргумент не предоставлен")
    void shouldPrintErrorWhenNoArgumentProvided() {
        // Given: пустой args

        // When
        DirectoryViewer.main(new String[]{});

        // Then
        String out = normalize(outContent.toString());
        String err = normalize(errContent.toString());

        assertThat(out).isEmpty();
        assertThat(err)
                .contains("Ошибка: Не указан путь к директории.")
                .contains("Использование: java ru.mentee.power.io.DirectoryViewer <путь_к_директории>");
    }
}

