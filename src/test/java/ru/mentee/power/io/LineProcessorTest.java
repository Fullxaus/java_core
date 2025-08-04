package ru.mentee.power.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты обработчика строк (LineProcessor)")
public class LineProcessorTest {

    @TempDir
    Path tempDir;

    private Path inputFile;
    private Path outputFile;

    @BeforeEach
    void setUp() {
        inputFile = tempDir.resolve("input_test.txt");
        outputFile = tempDir.resolve("output_test.txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        // Удаляем файлы после каждого теста
        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(outputFile);
    }

    @Test
    @DisplayName("Должен корректно обработать файл с несколькими строками разного регистра")
    void shouldProcessFileWithMixedCaseLines() throws IOException {
        List<String> lines = List.of("Первая строка.", "Вторая строка.", "третья строка.");
        Files.write(inputFile, lines);

        // Запускаем метод main с указанием имен файлов
        LineProcessor.main(new String[]{inputFile.toString(), outputFile.toString()});

        // Проверяем, что выходной файл был создан
        assertThat(Files.exists(outputFile)).isTrue();

        List<String> outputLines = Files.readAllLines(outputFile);
        assertThat(outputLines).containsExactly("ПЕРВАЯ СТРОКА.", "ВТОРАЯ СТРОКА.", "ТРЕТЬЯ СТРОКА.");
    }


    @Test
    @DisplayName("Должен корректно обработать пустой входной файл")
    void shouldProcessEmptyInputFile() throws IOException {
        // Создаем пустой входной файл
        Files.createFile(inputFile);

        // Запускаем метод main с указанием имен файлов
        LineProcessor.main(new String[]{inputFile.toString(), outputFile.toString()});

        // Проверяем, что выходной файл был создан
        assertThat(Files.exists(outputFile)).isTrue();

        // Проверяем, что выходной файл пуст
        assertThat(Files.size(outputFile)).isEqualTo(0);
    }

    @Test
    @DisplayName("Должен перезаписать существующий выходной файл")
    void shouldOverwriteExistingOutputFile() throws IOException {
        List<String> lines = List.of("Тестовая строка.");
        Files.write(inputFile, lines);
        Files.write(outputFile, List.of("Старое содержимое."));

        LineProcessor.main(new String[]{inputFile.toString(), outputFile.toString()});

        List<String> outputLines = Files.readAllLines(outputFile);
        assertThat(outputLines).containsExactly("ТЕСТОВАЯ СТРОКА.");
    }

    @Test
    @DisplayName("Должен создать входной файл по умолчанию, если он не существует")
    void shouldCreateDefaultInputFileIfNotExists() throws IOException {
        // Формируем абсолютные пути к файлам
        Path inputFile = Paths.get("input.txt").toAbsolutePath();
        Path outputFile = Paths.get("output.txt").toAbsolutePath();

        // Удаляем файлы на случай, если они существуют с прошлых запусков
        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(outputFile);

        System.out.println("Input file path: " + inputFile);
        System.out.println("Output file path: " + outputFile);

        // Проверяем что входной файл действительно не существует
        assertThat(Files.exists(inputFile)).isFalse();

        // Запускаем главный метод, передавая абсолютные пути к файлам
        LineProcessor.main(new String[]{inputFile.toString(), outputFile.toString()});

        // После выполнения main() проверяем, что входной файл был создан
        assertThat(Files.exists(inputFile)).isTrue();

        // Проверяем, что выходной файл существует и содержит строки по умолчанию
        assertThat(Files.exists(outputFile)).isTrue();
        List<String> outputLines = Files.readAllLines(outputFile);
        assertThat(outputLines).containsExactly(
                "ПЕРВАЯ СТРОКА.",
                "SECOND LINE WITH MIXED CASE",
                "ТРЕТЬЯ"
        );
    }
}
