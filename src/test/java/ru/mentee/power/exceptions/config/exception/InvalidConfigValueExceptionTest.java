package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InvalidConfigValueExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом и значением")
    void shouldCreateExceptionWithMessageKeyAndValue() {
        InvalidConfigValueException ex =
                new InvalidConfigValueException("bad format", "theKey", "xyz");

        // текст сообщения
        assertThat(ex).hasMessage("bad format");
        // геттеры key/value
        assertThat(ex.getKey()).isEqualTo("theKey");
        assertThat(ex.getValue()).isEqualTo("xyz");
        // причина не передавалась
        assertThat(ex.getCause()).isNull();
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом, значением и причиной")
    void shouldCreateExceptionWithMessageKeyValueAndCause() {
        NumberFormatException cause = new NumberFormatException("not a number");
        InvalidConfigValueException ex =
                new InvalidConfigValueException("bad format", "numKey", "notnum", cause);

        assertThat(ex).hasMessage("bad format");
        assertThat(ex.getKey()).isEqualTo("numKey");
        assertThat(ex.getValue()).isEqualTo("notnum");
        // причина должна совпадать
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("Должен вернуть ключ, для которого значение некорректно")
    void shouldReturnKey() {
        InvalidConfigValueException ex =
                new InvalidConfigValueException("err", "k1", "val1");

        assertThat(ex.getKey()).isEqualTo("k1");
    }

    @Test
    @DisplayName("Должен вернуть некорректное значение")
    void shouldReturnInvalidValue() {
        InvalidConfigValueException ex =
                new InvalidConfigValueException("err", "k1", "badVal");

        assertThat(ex.getValue()).isEqualTo("badVal");
    }

}
