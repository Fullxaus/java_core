package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты менеджера данных HighScoreManager")
public class HighScoreManagerTest {

    @TempDir
    Path tempDir;

    private Path testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_scores.dat"); // Временный путь к файлу
    }

    @Test
    @DisplayName("Должен сохранять и загружать пустой список")
    void shouldSaveAndLoadEmptyList() throws IOException {
        // Arrange: создаем пустой список
        List<HighScoreEntryClass> emptyList = new ArrayList<>();

        // Act: сохраняем пустой список
        HighScoreManager.saveScores(emptyList, testFilePath.toString());

        // Assert: проверяем, что загружается именно пустой список
        List<HighScoreEntryClass> loadedList = HighScoreManager.loadScores(testFilePath.toString());
        assertThat(loadedList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Должен сохранять и загружать список с несколькими записями")
    void shouldSaveAndLoadPopulatedList() throws IOException {
        // Arrange: создаём список с двумя объектами
        List<HighScoreEntryClass> populatedList = new ArrayList<>();
        populatedList.add(new HighScoreEntryClass("Alice", 1200));
        populatedList.add(new HighScoreEntryClass("Bob", 1000));

        // Act: сохраняем список
        HighScoreManager.saveScores(populatedList, testFilePath.toString());

        // Assert: проверяем, что загрузились те же самые элементы
        List<HighScoreEntryClass> loadedList = HighScoreManager.loadScores(testFilePath.toString());
        assertThat(loadedList).hasSize(populatedList.size())
                .containsExactlyInAnyOrderElementsOf(populatedList);
    }

    @Test
    @DisplayName("Должен возвращать пустой список при загрузке несуществующего файла")
    void shouldReturnEmptyListForNonExistentFile() {
        // Arrange: используем произвольный несуществующий путь к файлу
        String nonExistingFilePath = "nonexistent_file.dat";

        // Act: пытаемся загрузить несуществующий файл
        List<HighScoreEntryClass> result = HighScoreManager.loadScores(nonExistingFilePath);

        // Assert: убеждаемся, что возвращается пустой список
        assertThat(result).isNotNull().isEmpty();
    }
}