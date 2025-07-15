package ru.mentee.power.collections.base;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class SimpleCollectionTasksTest {

    @Test
    void shouldReturnCorrectCountForStringsStartingWithGivenLetter() {
        List<String> names = Arrays.asList("Alice", "Bob", "Anna", "Alexander");
        char letter = 'A';

        int result = SimpleCollectionTasks.countStringsStartingWith(names, letter);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        List<String> emptyList = List.of();

        int result = SimpleCollectionTasks.countStringsStartingWith(emptyList, 'A');

        assertThat(result).isZero();
    }

    @Test
    void shouldReturnZeroForNullList() {
        List<String> nullList = null;

        int result = SimpleCollectionTasks.countStringsStartingWith(nullList, 'A');

        assertThat(result).isZero();
    }

    @Test
    void shouldIgnoreNullAndEmptyStringsInList() {
        List<String> list = Arrays.asList(null, "", "Apple", " ", "aAm", null);

        int result = SimpleCollectionTasks.countStringsStartingWith(list, 'A');

        assertThat(result).isEqualTo(2);  // "Apple" и "aAm" считаются
    }

    @Test
    void shouldBeCaseInsensitiveWhenComparingLetters() {
        List<String> list = Arrays.asList("apple", "Banana", "Apricot", "avocado", "Berry");

        int result = SimpleCollectionTasks.countStringsStartingWith(list, 'a');

        assertThat(result).isEqualTo(3); // apple, Apricot, avocado
    }

    @Test
    void shouldHandleCustomScenario() {
        List<String> list = Arrays.asList("1Apple", "!Anna", null, "alice", "ALex", "", "bob", "Alexander");

        int result = SimpleCollectionTasks.countStringsStartingWith(list, 'A');

        // Должны посчитать только строки, начинающиеся на 'A' (без учета регистра):
        // "alice", "ALex", "Alexander"
        assertThat(result).isEqualTo(3);
    }
}