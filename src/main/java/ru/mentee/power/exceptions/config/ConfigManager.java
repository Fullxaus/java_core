package ru.mentee.power.exceptions.config;

import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.Map;
import java.util.HashMap;

/**
 * Класс для работы с конфигурационными параметрами.
 */
public class ConfigManager {
    private final Map<String, String> config;

    /**
     * Создает новый менеджер конфигурации с указанной картой параметров.
     *
     * @param config Карта конфигурационных параметров
     */
    public ConfigManager(Map<String, String> config) {
        // клонируем, чтобы внешний код не мог менять нашу внутреннюю карту
        this.config = new HashMap<>(config);
    }

    /**
     * Создает новый менеджер конфигурации с пустой картой параметров.
     */
    public ConfigManager() {
        this.config = new HashMap<>();
    }

    /**
     * Получает значение по ключу, выбрасывая исключение, если ключ не найден.
     *
     * @param key Ключ для поиска.
     * @return Значение параметра.
     * @throws MissingConfigKeyException Если ключ отсутствует в конфигурации.
     */
    public String getRequiredValue(String key) throws MissingConfigKeyException {
        if (!config.containsKey(key)) {
            throw new MissingConfigKeyException("Missing config key",key);
        }
        return config.get(key);
    }

    /**
     * Получает числовое значение по ключу, выбрасывая исключение, если значение не является числом.
     *
     * @param key Ключ для поиска.
     * @return Числовое значение параметра.
     * @throws MissingConfigKeyException  Если ключ отсутствует в конфигурации.
     * @throws InvalidConfigValueException Если значение не является числом.
     */
    public int getRequiredIntValue(String key)
            throws MissingConfigKeyException, InvalidConfigValueException {
        String raw = getRequiredValue(key);
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException nfe) {
            throw new InvalidConfigValueException(
                    "Invalid int for key",
                    key,
                    raw,
                    nfe
            );
        }
    }

    /**
     * Получает булево значение по ключу, выбрасывая исключение, если значение не является булевым.
     *
     * @param key Ключ для поиска.
     * @return Булево значение параметра.
     * @throws MissingConfigKeyException  Если ключ отсутствует в конфигурации.
     * @throws InvalidConfigValueException Если значение не является булевым.
     */
    public boolean getRequiredBooleanValue(String key)
            throws MissingConfigKeyException, InvalidConfigValueException {
        String raw = getRequiredValue(key);
        // допускаем только "true" или "false" (без учета регистра)
        if ("true".equalsIgnoreCase(raw)) {
            return true;
        }
        if ("false".equalsIgnoreCase(raw)) {
            return false;
        }
        throw new InvalidConfigValueException(
                "Invalid boolean for key",
                key,
                raw
        );
    }

    /**
     * Добавляет или обновляет параметр в конфигурации.
     *
     * @param key   Ключ параметра
     * @param value Значение параметра
     */
    public void setValue(String key, String value) {
        config.put(key, value);
    }
}