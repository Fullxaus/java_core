package ru.mentee.power.methods;

public class StringUtilsDemo {
    public static void main(String[] args) {
        // Пример использования метода countChars
        String text = "Hello, World!";
        char target = 'l';
        int count = StringUtils.countChars(text, target);
        System.out.println("Символ '" + target + "' встречается " + count + " раз(а) в строке \"" + text + "\"");

        // Пример использования метода truncate
        String longText = "Это длинный текст, который нужно обрезать";
        String truncated = StringUtils.truncate(longText, 10);
        System.out.println("Исходный текст: \"" + longText + "\"");
        System.out.println("Обрезанный текст: \"" + truncated + "\"");

        // Пример использования метода isPalindrome
        String palindrome = "А роза упала на лапу Азора";
        boolean isPal = StringUtils.isPalindrome(palindrome);
        System.out.println("Строка \"" + palindrome + "\" " + (isPal ? "является" : "не является") + " палиндромом");

        // Пример использования метода normalizeSpaces
        String withExtraSpaces = "   Много    лишних     пробелов   ";
        String normalized = StringUtils.normalizeSpaces(withExtraSpaces);
        System.out.println("Исходная строка: \"" + withExtraSpaces + "\"");
        System.out.println("После нормализации: \"" + normalized + "\"");

        // Пример использования метода join
        String[] words = {"Яблоко", "Банан", "Груша", "Апельсин"};
        String joined = StringUtils.join(words, ", ");
        System.out.println("Объединенные слова: " + joined);
    }
}
