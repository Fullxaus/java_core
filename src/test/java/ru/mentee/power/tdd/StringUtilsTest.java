package ru.mentee.power.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты для утилиты работы со строками")
public class StringUtilsTest {

    private StringUtils stringUtils;

    @BeforeEach
    void setUp() {
        stringUtils = new StringUtils();
    }

    @Test
    @DisplayName("Переворот обычной строки")
    void shouldReverseNormalString() {
        // Arrange
        String original = "hello";
        String expected = "olleh";

        // Act
        String actual = stringUtils.reverse(original);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Переворот пустой строки")
    void shouldReturnEmptyStringWhenInputIsEmpty() {
        // Arrange
        String original = "";
        String expected = "";

        // Act
        String actual = stringUtils.reverse(original);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Возврат null при null на входе")
    void shouldReturnNullWhenInputIsNull() {
        // Arrange
        String original = null;

        // Act
        String actual = stringUtils.reverse(original);

        // Assert
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Переворот строки с одним символом")
    void shouldReturnSameStringWhenSingleCharacter() {
        // Arrange
        String original = "a";
        String expected = "a";

        // Act
        String actual = stringUtils.reverse(original);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Переворот строки-палиндрома")
    void shouldReturnSameStringForPalindrome() {
        // Arrange
        String original = "madam";
        String expected = "madam";

        // Act
        String actual = stringUtils.reverse(original);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Переворот строки с пробелами и цифрами")
    void shouldReverseStringWithSpacesAndDigits() {
        // Arrange
        String original = "123 abc 456";
        String expected = "654 cba 321";

        // Act
        String actual = stringUtils.reverse(original);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}