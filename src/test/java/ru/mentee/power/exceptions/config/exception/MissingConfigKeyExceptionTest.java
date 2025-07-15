package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MissingConfigKeyExceptionTest {

    @Test
    @DisplayName("Должен создать исключение с сообщением и ключом")
    void shouldCreateExceptionWithMessageAndKey() {
        MissingConfigKeyException ex =
                new MissingConfigKeyException("key not found", "myKey");

        // проверяем сообщение
        assertThat(ex).hasMessage("key not found");
        // проверяем геттер для missingKey
        assertThat(ex.getMissingKey()).isEqualTo("myKey");
        // у этого конструктора нет причины
        assertThat(ex.getCause()).isNull();
    }

    @Test
    @DisplayName("Должен создать исключение с сообщением, ключом и причиной")
    void shouldCreateExceptionWithMessageKeyAndCause() {
        Throwable cause = new IllegalStateException("bad state");
        MissingConfigKeyException ex =
                new MissingConfigKeyException("missing", "anotherKey", cause);

        assertThat(ex).hasMessage("missing");
        assertThat(ex.getMissingKey()).isEqualTo("anotherKey");
        // причина та же самая
        assertThat(ex.getCause()).isSameAs(cause);
    }

    @Test
    @DisplayName("Должен вернуть ключ, который отсутствует в конфигурации")
    void shouldReturnMissingKey() {
        MissingConfigKeyException ex =
                new MissingConfigKeyException("no key", "abc123");

        assertThat(ex.getMissingKey()).isEqualTo("abc123");
    }

}
