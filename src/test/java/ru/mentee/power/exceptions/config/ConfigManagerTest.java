package ru.mentee.power.exceptions.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ConfigManagerTest {

    private ConfigManager configManager;
    private Map<String, String> testConfig;

    @BeforeEach
    void setUp() {
        testConfig = new HashMap<>();
        testConfig.put("stringKey", "hello");
        testConfig.put("intKey", "42");
        testConfig.put("badInt", "forty-two");
        testConfig.put("trueKey", "TRUE");
        testConfig.put("falseKey", "false");
        testConfig.put("badBool", "not_bool");

        configManager = new ConfigManager(testConfig);
    }

    @Test
    @DisplayName("Должен успешно получить строковое значение по существующему ключу")
    void shouldGetStringValueWhenKeyExists() throws MissingConfigKeyException {
        assertThat(configManager.getRequiredValue("stringKey")).isEqualTo("hello");
    }

    @Test
    @DisplayName("Должен выбросить MissingConfigKeyException при запросе несуществующего ключа")
    void shouldThrowMissingConfigKeyExceptionWhenKeyDoesNotExist() {
        assertThatThrownBy(() -> configManager.getRequiredValue("noKey"))
                .isInstanceOf(MissingConfigKeyException.class)
                .satisfies(ex -> {
                    MissingConfigKeyException me = (MissingConfigKeyException) ex;
                    assertThat(me.getMissingKey()).isEqualTo("noKey");
                });
    }

    @Test
    @DisplayName("Должен успешно получить целочисленное значение по существующему ключу")
    void shouldGetIntValueWhenKeyExists() throws Exception {
        assertThat(configManager.getRequiredIntValue("intKey")).isEqualTo(42);
    }

    @Test
    @DisplayName("Должен выбросить InvalidConfigValueException при некорректном int")
    void shouldThrowInvalidConfigValueExceptionWhenIntValueIsInvalid() {
        assertThatThrownBy(() -> configManager.getRequiredIntValue("badInt"))
                .isInstanceOf(InvalidConfigValueException.class)
                .satisfies(ex -> {
                    InvalidConfigValueException ice = (InvalidConfigValueException) ex;
                    assertThat(ice.getKey()).isEqualTo("badInt");
                    assertThat(ice.getValue()).isEqualTo("forty-two");
                });
    }

    @Test
    @DisplayName("Должен успешно получить булево значение по существующему ключу")
    void shouldGetBooleanValueWhenKeyExists() throws Exception {
        assertThat(configManager.getRequiredBooleanValue("trueKey")).isTrue();
        assertThat(configManager.getRequiredBooleanValue("falseKey")).isFalse();
    }

    @Test
    @DisplayName("Должен выбросить InvalidConfigValueException при некорректном boolean")
    void shouldThrowInvalidConfigValueExceptionWhenBooleanValueIsInvalid() {
        assertThatThrownBy(() -> configManager.getRequiredBooleanValue("badBool"))
                .isInstanceOf(InvalidConfigValueException.class)
                .satisfies(ex -> {
                    InvalidConfigValueException ice = (InvalidConfigValueException) ex;
                    assertThat(ice.getKey()).isEqualTo("badBool");
                    assertThat(ice.getValue()).isEqualTo("not_bool");
                });
    }

    @Test
    @DisplayName("Должен успешно добавить новое значение в конфигурацию")
    void shouldAddNewValueToConfig() throws MissingConfigKeyException {
        assertThatThrownBy(() -> configManager.getRequiredValue("newKey"))
                .isInstanceOf(MissingConfigKeyException.class);

        configManager.setValue("newKey", "newVal");
        assertThat(configManager.getRequiredValue("newKey")).isEqualTo("newVal");
    }

    @Test
    @DisplayName("Должен успешно обновить существующее значение в конфигурации")
    void shouldUpdateExistingValueInConfig() throws MissingConfigKeyException {
        assertThat(configManager.getRequiredValue("stringKey")).isEqualTo("hello");
        configManager.setValue("stringKey", "upd");
        assertThat(configManager.getRequiredValue("stringKey")).isEqualTo("upd");
    }
}
