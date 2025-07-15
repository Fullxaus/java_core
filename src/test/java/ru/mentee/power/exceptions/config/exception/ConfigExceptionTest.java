package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ConfigExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением")
    void shouldCreateExceptionWithMessage() {
        ConfigException ex = new ConfigException("oops");
        assertThat(ex).hasMessage("oops");
        assertThat(ex.getCause()).isNull();
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением и причиной")
    void shouldCreateExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("root");
        ConfigException ex = new ConfigException("oops", cause);
        assertThat(ex).hasMessage("oops");
        assertThat(ex.getCause()).isSameAs(cause);
    }
}
