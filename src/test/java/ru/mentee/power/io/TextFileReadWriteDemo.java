package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты записи и чтения строк (TextFileReadWriteDemo)")
class TextFileReadWriteDemoTest {

    @TempDir
    Path tempDir;

    Path testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_lines.txt");
    }

    // Вспомогательный метод для записи строк
    private void writeLines(Path path, List<String> linesToWrite) throws IOException {
        // Явно указываем тип BufferedWriter
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (String line : linesToWrite) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        }
    }

    // Вспомогательный метод для чтения посимвольно
    private String readChars(Path path) throws IOException {
        StringBuilder content = new StringBuilder();
        // Явно указываем тип BufferedReader
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            int charCode;
            while ((charCode = reader.read()) != -1) {
                content.append((char) charCode);
            }
        }
        return content.toString();
    }

    @Test
    @DisplayName("Должен корректно записать и прочитать несколько строк")
    void shouldWriteAndReadLinesCorrectly() throws IOException {
        List<String> linesToWrite = List.of("Строка 1", "Line 2", "Третья строка!");

        writeLines(testFilePath, linesToWrite);

        assertThat(testFilePath).exists();
        List<String> actualLines = Files.readAllLines(testFilePath, StandardCharsets.UTF_8);
        assertThat(actualLines).isEqualTo(linesToWrite);

        String readContent = readChars(testFilePath);
        String expectedContent = String.join(System.lineSeparator(), linesToWrite)
                + System.lineSeparator();
        assertThat(readContent).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("Должен корректно обработать запись пустого списка строк")
    void shouldHandleEmptyListWrite() throws IOException {
        List<String> emptyList = new ArrayList<>();

        writeLines(testFilePath, emptyList);

        assertThat(testFilePath).exists();
        assertThat(Files.size(testFilePath)).isZero();

        String readContent = readChars(testFilePath);
        assertThat(readContent).isEmpty();
    }

    @Test
    @DisplayName("Должен корректно записать и прочитать строки с различными символами")
    void shouldWriteAndReadSpecialChars() throws IOException {
        List<String> specialLines = List.of(
                "Табуляция:\tсимволы",
                "Новая\\nСтрока",
                "Символы:!@#$%^&*()_+=-`~"
        );

        writeLines(testFilePath, specialLines);

        assertThat(testFilePath).exists();
        List<String> actualLines = Files.readAllLines(testFilePath, StandardCharsets.UTF_8);
        assertThat(actualLines).isEqualTo(specialLines);

        String readContent = readChars(testFilePath);
        String expectedContent = String.join(System.lineSeparator(), specialLines)
                + System.lineSeparator();
        assertThat(readContent).isEqualTo(expectedContent);
    }
}
