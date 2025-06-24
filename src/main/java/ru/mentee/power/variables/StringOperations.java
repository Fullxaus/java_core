package ru.mentee.power.variables;

public class StringOperations {

  public static   String str1 = "Dmitrii";
   public static String str2 = "Vidru";
   public static char ch1 = 'D';
   public static char ch2 = 'V';

    public static void main(String[] args) {

        String concatResult = str1 + " " + str2;

        String charToString1 = Character.toString(ch1);
        String charToString2 = String.valueOf(ch2);

        int number = 123;
        String numberToString = Integer.toString(number);
        int stringToNumber = Integer.parseInt(numberToString);

        char extractedChar = concatResult.charAt(1);
        
        System.out.println("Конкатенация строк: " + concatResult);
        System.out.println("Преобразование символа 'D' в строку: " + charToString1);
        System.out.println("Преобразование символа 'V' в строку: " + charToString2);
        System.out.println("Число в строку: " + numberToString);
        System.out.println("Строка обратно в число: " + stringToNumber);
        System.out.println("Извлеченный символ из строки: " + extractedChar);
    }
}
