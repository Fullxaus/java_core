package ru.mentee.power.methods;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    public void testCountChars() {
        assertThat(StringUtils.countChars("hello", 'l')).isEqualTo(2);
        assertThat(StringUtils.countChars("hello", 'z')).isEqualTo(0);
        assertThat(StringUtils.countChars(null, 'a')).isEqualTo(0);
        assertThat(StringUtils.countChars("", 'a')).isEqualTo(0);
    }

    @Test
    public void testTruncate() {
        assertThat(StringUtils.truncate("hello", 5)).isEqualTo("hello");
        assertThat(StringUtils.truncate("hello world", 4)).isEqualTo("hell...");
        assertThat(StringUtils.truncate(null, 10)).isEmpty();
        assertThat(StringUtils.truncate("", 10)).isEmpty();
        assertThat(StringUtils.truncate("hello world", 5)).isEqualTo("hello...");
    }

    @Test
    public void testIsPalindrome() {
        assertThat(StringUtils.isPalindrome("level")).isTrue();
        assertThat(StringUtils.isPalindrome("A man, a plan, a canal, Panama")).isTrue();
        assertThat(StringUtils.isPalindrome("hello")).isFalse();
        assertThat(StringUtils.isPalindrome(null)).isFalse();
        assertThat(StringUtils.isPalindrome("Madam, I'm Adam")).isTrue();
    }

    @Test
    public void testNormalizeSpaces() {
        assertThat(StringUtils.normalizeSpaces("hello   world")).isEqualTo("hello world");
        assertThat(StringUtils.normalizeSpaces("  hello world  ")).isEqualTo("hello world");
        assertThat(StringUtils.normalizeSpaces("hello\tworld")).isEqualTo("hello world");
        assertThat(StringUtils.normalizeSpaces("hello\nworld")).isEqualTo("hello world");
        assertThat(StringUtils.normalizeSpaces(null)).isEmpty();
    }

    @Test
    public void testJoin() {
        assertThat(StringUtils.join(new String[]{"a", "b", "c"}, ",")).isEqualTo("a,b,c");
        assertThat(StringUtils.join(new String[]{"a", "b", "c"}, "-")).isEqualTo("a-b-c");
        assertThat(StringUtils.join(new String[]{"a", "b", "c"}, "")).isEqualTo("abc");
        assertThat(StringUtils.join(new String[]{"a", null, "c"}, ",")).isEqualTo("a,c");
        assertThat(StringUtils.join(null, ",")).isEmpty();
        assertThat(StringUtils.join(new String[]{}, ",")).isEmpty();
    }
}
