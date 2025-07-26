package ru.mentee.power.collections.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Класс для работы с конфигурационными данными в формате "ключ=значение".
 */
public class ConfigParser {

    private final Map<String, String> configMap;

    /**
     * Создает пустой объект ConfigParser.
     */
    public ConfigParser() {
        this.configMap = new LinkedHashMap<>();
    }

    /**
     * Парсит строку конфигурации в формате "ключ=значение".
     * Каждая настройка должна быть на отдельной строке.
     * Строки, начинающиеся с #, считаются комментариями и игнорируются.
     * Пустые строки игнорируются.
     *
     * @param configString строка конфигурации (не {@code null})
     * @throws IllegalArgumentException если {@code configString} равен {@code null} либо
     *                                  встречена неверная запись без символа '='
     */
    public void parseConfig(String configString) {
        if (configString == null) {
            throw new IllegalArgumentException("configString cannot be null");
        }

        String[] lines = configString.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String raw = lines[i].trim();
            if (raw.isEmpty() || raw.startsWith("#")) {
                continue;
            }

            int eqPos = raw.indexOf('=');
            if (eqPos < 0) {
                throw new IllegalArgumentException(
                        String.format(
                                "Line %d is not a valid key=value pair: '%s'", i + 1, lines[i]));
            }

            String key = raw.substring(0, eqPos).trim();
            String value = raw.substring(eqPos + 1).trim();

            if (key.isEmpty()) {
                throw new IllegalArgumentException(
                        String.format("Line %d has an empty key: '%s'", i + 1, lines[i]));
            }

            configMap.put(key, value);
        }
    }

    /**
     * Преобразует текущую конфигурацию в строку.
     *
     * @return строка конфигурации в формате "ключ=значение" с разделителями новой строки
     */
    public String toConfigString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            sb.append(entry.getKey())
                    .append('=')
                    .append(entry.getValue())
                    .append('\n');
        }
        // удалим последний перевод строки, если он есть
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Получает значение по ключу.
     *
     * @param key ключ (не {@code null})
     * @return значение или {@code null}, если ключ не найден
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public String getValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return configMap.get(key);
    }

    /**
     * Получает значение по ключу или возвращает значение по умолчанию.
     *
     * @param key          ключ (не {@code null})
     * @param defaultValue значение по умолчанию
     * @return значение или {@code defaultValue}, если ключ не найден
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public String getValue(String key, String defaultValue) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return configMap.getOrDefault(key, defaultValue);
    }

    /**
     * Устанавливает значение для ключа.
     *
     * @param key   ключ (не {@code null})
     * @param value значение (не {@code null})
     * @return предыдущее значение или {@code null}, если ключ не существовал
     * @throws IllegalArgumentException если {@code key} или {@code value} равны {@code null}
     */
    public String setValue(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
        return configMap.put(key, value);
    }

    /**
     * Удаляет ключ и его значение.
     *
     * @param key ключ
     * @return {@code true}, если ключ существовал и был удален
     */
    public boolean removeKey(String key) {
        if (key == null) {
            return false;
        }
        return configMap.remove(key) != null;
    }

    /**
     * Проверяет наличие ключа.
     *
     * @param key ключ
     * @return {@code true}, если ключ существует
     */
    public boolean containsKey(String key) {
        if (key == null) {
            return false;
        }
        return configMap.containsKey(key);
    }

    /**
     * Возвращает все ключи.
     *
     * @return множество ключей (неизменяемое)
     */
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(configMap.keySet());
    }

    /**
     * Возвращает все пары ключ-значение.
     *
     * @return карта с парами ключ-значение (неизменяемая)
     */
    public Map<String, String> getAll() {
        return Collections.unmodifiableMap(configMap);
    }

    /**
     * Получает целочисленное значение по ключу.
     *
     * @param key ключ (не {@code null})
     * @return целое число
     * @throws NoSuchElementException   если ключ не найден
     * @throws NumberFormatException    если значение не является числом
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public int getIntValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        String raw = getValue(key);
        if (raw == null) {
            throw new NoSuchElementException("Key '" + key + "' not found");
        }
        return Integer.parseInt(raw);
    }

    /**
     * Получает логическое значение по ключу.
     * Строки "true", "yes", "1" (игнорируя регистр) считаются true.
     * Все остальные значения считаются false.
     *
     * @param key ключ (не {@code null})
     * @return логическое значение
     * @throws NoSuchElementException   если ключ не найден
     * @throws IllegalArgumentException если {@code key} равен {@code null}
     */
    public boolean getBooleanValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        String raw = getValue(key);
        if (raw == null) {
            throw new NoSuchElementException("Key '" + key + "' not found");
        }
        String val = raw.trim().toLowerCase(Locale.ROOT);
        return val.equals("true") || val.equals("yes") || val.equals("1");
    }

    /**
     * Получает список значений, разделенных запятыми.
     *
     * @param key ключ
     * @return список значений или пустой список, если ключ не найден или значение пустое
     */
    public List<String> getListValue(String key) {
        String raw = getValue(key);
        if (raw == null || raw.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String[] items = raw.split(",");
        List<String> result = new ArrayList<>(items.length);
        for (String s : items) {
            result.add(s.trim());
        }
        return result;
    }

    /**
     * Очищает все настройки.
     */
    public void clear() {
        configMap.clear();
    }

    /**
     * Возвращает количество пар ключ-значение.
     *
     * @return количество пар
     */
    public int size() {
        return configMap.size();
    }
}