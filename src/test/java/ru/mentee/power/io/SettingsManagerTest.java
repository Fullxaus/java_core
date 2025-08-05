package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DisplayName("Tests for the Settings Manager (SettingsManager)")
public class SettingsManagerTest {

    @TempDir
    Path tempDir;

    private Path testFilePath;
    private List<Serializable> testSettingsList;
    private ServerConfiguration testServerConfig;
    private SettingsManager.WindowConfiguration testWindowConfig;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_settings.ser");

        testServerConfig = new ServerConfiguration("test.server", 1234, true);
        testServerConfig.setLastStatus("Initial"); // Устанавливаем transient-поле
        testWindowConfig = new SettingsManager.WindowConfiguration("Test Window", 800, 600);

        testSettingsList = new ArrayList<>();
        testSettingsList.add(testServerConfig);
        testSettingsList.add(testWindowConfig);
    }

    @Test
    @DisplayName("Must save and load list with mixed types of Serializable objects")
    void shouldSaveAndLoadListOfMixedSerializable() throws IOException, ClassNotFoundException {
        // Given (in setup method)

        // When
        SettingsManager.saveSettings(testSettingsList, testFilePath.toString());
        List<Serializable> loadedList = SettingsManager.loadSettings(testFilePath.toString());

        // Then
        assertThat(loadedList).as("Loaded list").isNotNull().hasSameSizeAs(testSettingsList);

        // Check first element as ServerConfiguration
        assertThat(loadedList.get(0))
                .isInstanceOf(ServerConfiguration.class)
                .extracting("serverAddress", "serverPort", "loggingEnabled")
                .containsExactly("test.server", 1234, true);

        // Verify transient field was not serialized
        assertThat(((ServerConfiguration) loadedList.get(0)).getLastStatus()).isNullOrEmpty();

        // Check second element as WindowConfiguration
        assertThat(loadedList.get(1))
                .isInstanceOf(SettingsManager.WindowConfiguration.class)
                .extracting("windowTitle", "width", "height")
                .containsExactly("Test Window", 800, 600);
    }

    @Test
    @DisplayName("Must save and load an empty list correctly")
    void shouldSaveAndLoadEmptyList() throws IOException, ClassNotFoundException {
        // Given
        List<Serializable> emptyList = new ArrayList<>();

        // When
        SettingsManager.saveSettings(emptyList, testFilePath.toString());
        List<Serializable> loadedList = SettingsManager.loadSettings(testFilePath.toString());

        // Then
        assertThat(loadedList).as("Loaded empty list").isNotNull().isEmpty();
    }

    @Test
    @DisplayName("loadSettings must return an empty list when the file does not exist")
    void loadShouldReturnEmptyListWhenFileNotExists() {
        // Given: The file doesn't exist
        assertThat(testFilePath).doesNotExist();

        // When
        List<Serializable> loadedList = SettingsManager.loadSettings(testFilePath.toString());

        // Then
        assertThat(loadedList).as("Loading from missing file").isNotNull().isEmpty();
    }

    @Test
    @DisplayName("loadSettings must return an empty list on deserialization error")
    void loadShouldReturnEmptyListOnDeserializationError() throws IOException {
        // Given: Create a file with invalid data (not a serialized object)
        Files.writeString(testFilePath, "This is not a serialized list");

        // When
        List<Serializable> loadedList = SettingsManager.loadSettings(testFilePath.toString());

        // Then
        assertThat(loadedList).as("After loading corrupt file").isNotNull().isEmpty();
    }
}