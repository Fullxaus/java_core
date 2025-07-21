package ru.mentee.power.collections.map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigParserTest {

    @Test
    @DisplayName("Метод setValue должен добавлять и возвращать предыдущее значение")
    void shouldSetValueAndReturnPreviousValue() {
        ConfigParser parser = new ConfigParser();

        assertThat(parser.setValue("host", "localhost")).isNull();
        assertThat(parser.setValue("port", "8080")).isNull();

        assertThat(parser.setValue("host", "127.0.0.1")).isEqualTo("localhost");
        assertThat(parser.getValue("host")).isEqualTo("127.0.0.1");
    }

    @Test
    @DisplayName("Метод setValue должен выбрасывать исключение при null аргументах")
    void shouldThrowExceptionForNullArgumentsInSetValue() {
        ConfigParser parser = new ConfigParser();

        assertThatThrownBy(() -> parser.setValue(null, "value"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> parser.setValue("key", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод getValue должен возвращать значение по ключу")
    void shouldGetValueByKey() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("user", "admin");
        assertThat(parser.getValue("user")).isEqualTo("admin");
        // если ключ отсутствует, должен вернуть null
        assertThat(parser.getValue("missingKey")).isNull();
    }

    @Test
    @DisplayName("Метод getValue должен возвращать defaultValue, если ключ не найден")
    void shouldReturnDefaultValueIfKeyNotFound() {
        ConfigParser parser = new ConfigParser();
        String defaultVal = "default";
        assertThat(parser.getValue("noKey", defaultVal)).isEqualTo(defaultVal);
    }

    @Test
    @DisplayName("Метод getValue с defaultValue должен выбрасывать исключение при null ключе")
    void shouldThrowExceptionForNullKeyInGetValueWithDefault() {
        ConfigParser parser = new ConfigParser();
        assertThatThrownBy(() -> parser.getValue(null, "any"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод removeKey должен удалять ключ и возвращать true, если ключ существовал")
    void shouldRemoveKeyAndReturnTrueIfKeyExists() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("host", "localhost");
        parser.setValue("port", "8080");

        assertTrue(parser.removeKey("host"));
        assertFalse(parser.containsKey("host"));
        assertEquals(1, parser.size());
    }

    @Test
    @DisplayName("Метод removeKey должен возвращать false, если ключ не существовал")
    void shouldReturnFalseIfKeyDidNotExist() {
        ConfigParser parser = new ConfigParser();
        assertFalse(parser.removeKey("unknown"));
    }

    @Test
    @DisplayName("Метод containsKey должен корректно проверять наличие ключа")
    void shouldCheckIfKeyExists() {
        ConfigParser parser = new ConfigParser();
        assertFalse(parser.containsKey("a"));
        parser.setValue("a", "value");
        assertTrue(parser.containsKey("a"));
    }

    @Test
    @DisplayName("Метод getKeys должен возвращать все ключи")
    void shouldReturnAllKeys() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("one", "1");
        parser.setValue("two", "2");
        parser.setValue("three", "3");

        Set<String> keys = parser.getKeys();
        assertThat(keys).containsExactlyInAnyOrder("one", "two", "three");
    }

    @Test
    @DisplayName("Метод getAll должен возвращать копию всех пар ключ-значение")
    void shouldReturnCopyOfAllEntries() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("k1", "v1");
        parser.setValue("k2", "v2");

        Map<String, String> all = parser.getAll();
        assertThat(all).containsExactlyInAnyOrderEntriesOf(Map.of("k1", "v1", "k2", "v2"));

        // проверим, что возвращённая карта неизменяема
        assertThatThrownBy(() -> all.put("new", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Метод getIntValue должен корректно парсить целые числа")
    void shouldParseIntegerValues() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("num", "42");
        assertThat(parser.getIntValue("num")).isEqualTo(42);
    }

    @Test
    @DisplayName("Метод getIntValue должен выбрасывать исключение, если значение не является числом")
    void shouldThrowExceptionIfValueIsNotANumber() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("num", "notANumber");
        assertThatThrownBy(() -> parser.getIntValue("num"))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("Метод getBooleanValue должен корректно распознавать логические значения")
    void shouldParseBooleanValues() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("b1", "true");
        parser.setValue("b2", "YeS");
        parser.setValue("b3", "1");
        parser.setValue("b4", "nope");

        assertTrue(parser.getBooleanValue("b1"));
        assertTrue(parser.getBooleanValue("b2"));
        assertTrue(parser.getBooleanValue("b3"));
        assertFalse(parser.getBooleanValue("b4"));
    }

    @Test
    @DisplayName("Метод getListValue должен разбивать строку на список по запятым")
    void shouldSplitStringByCommas() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("colors", "red,green,blue");

        List<String> expected = Arrays.asList("red", "green", "blue");
        List<String> result = parser.getListValue("colors");

        assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("Метод getListValue должен возвращать пустой список для несуществующего ключа")
    void shouldReturnEmptyListForNonExistentKey() {
        ConfigParser parser = new ConfigParser();
        List<String> list = parser.getListValue("absent");
        assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("Метод parseConfig должен корректно парсить конфигурационную строку")
    void shouldParseConfigString() {
        String config = "# Это комментарий\n"
                + "host=localhost\n"
                + "port=8080\n"
                + "\n"
                + "debug=true";

        ConfigParser parser = new ConfigParser();
        parser.parseConfig(config);

        assertThat(parser.size()).isEqualTo(3);
        assertThat(parser.getValue("host")).isEqualTo("localhost");
        assertThat(parser.getValue("port")).isEqualTo("8080");
        assertThat(parser.getValue("debug")).isEqualTo("true");
    }

    @Test
    @DisplayName("Метод parseConfig должен выбрасывать исключение при null аргументе")
    void shouldThrowExceptionWhenConfigStringIsNull() {
        ConfigParser parser = new ConfigParser();
        assertThatThrownBy(() -> parser.parseConfig(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Метод toConfigString должен корректно преобразовывать конфигурацию в строку")
    void shouldConvertConfigToString() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("a", "1");
        parser.setValue("b", "2");
        parser.setValue("c", "3");

        String cfgString = parser.toConfigString();
        // порядок сохранения – как вставлялись
        String[] lines = cfgString.split("\n");
        assertThat(lines).containsExactly("a=1", "b=2", "c=3");
    }

    @Test
    @DisplayName("Метод clear должен удалять все пары ключ-значение")
    void shouldClearAllEntries() {
        ConfigParser parser = new ConfigParser();
        parser.setValue("x", "x");
        parser.setValue("y", "y");
        assertThat(parser.size()).isGreaterThan(0);

        parser.clear();
        assertThat(parser.size()).isZero();
        assertThat(parser.getKeys()).isEmpty();
    }

    @Test
    @DisplayName("Метод size должен возвращать правильное количество пар")
    void shouldReturnCorrectSize() {
        ConfigParser parser = new ConfigParser();
        assertThat(parser.size()).isZero();
        parser.setValue("one", "1");
        assertThat(parser.size()).isEqualTo(1);
        parser.setValue("two", "2");
        assertThat(parser.size()).isEqualTo(2);
        parser.removeKey("one");
        assertThat(parser.size()).isEqualTo(1);
    }
}
