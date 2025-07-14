package ru.mentee.power.methods;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataFormatterTest {

    @Test
    public void testFormatInt() {
        assertEquals("1 234 567", DataFormatter.format(1234567));
        assertEquals("0", DataFormatter.format(0));
        assertEquals("-1 234 567", DataFormatter.format(-1234567));
    }

    @Test
    public void testFormatIntWithPrefixAndSuffix() {
        assertEquals("$1 234 567 USD", DataFormatter.format(1234567, "$", " USD"));
        assertEquals("€0 EUR",       DataFormatter.format(0, "€", " EUR"));
    }

    @Test
    public void testFormatDouble() {
        assertEquals("1 234,57", DataFormatter.format(1234.567));
        assertEquals("0,00", DataFormatter.format(0.0));
        assertEquals("-1 234,57", DataFormatter.format(-1234.567));
    }

    @Test
    public void testFormatDoubleWithDecimalPlaces() {
        assertEquals("1 234,5670", DataFormatter.format(1234.567, 4));
        assertEquals("1 234,57", DataFormatter.format(1234.567, 2));
        assertEquals("1 235", DataFormatter.format(1234.567, 0));
    }

    @Test
    public void testFormatDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2023-01-15");

        assertEquals("15.01.2023", DataFormatter.format(date));
    }

    @Test
    public void testFormatDateWithPattern() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2023-01-15");

        assertEquals("15 January 2023", DataFormatter.format(date, "dd MMMM yyyy"));
        assertEquals("2023/01/15", DataFormatter.format(date, "yyyy/MM/dd"));
    }

    @Test
    public void testFormatList() {
        List<String> fruits = Arrays.asList("apple", "banana", "orange");
        assertEquals("apple, banana, orange", DataFormatter.format(fruits));

        List<String> empty = Arrays.asList();
        assertEquals("", DataFormatter.format(empty));
    }

    @Test
    public void testFormatListWithDelimiter() {
        List<String> fruits = Arrays.asList("apple", "banana", "orange");
        assertEquals("apple | banana | orange", DataFormatter.format(fruits, " | "));
        assertEquals("apple-banana-orange", DataFormatter.format(fruits, "-"));

        List<String> empty = Arrays.asList();
        assertEquals("", DataFormatter.format(empty, ";"));
    }
}
