package ru.mentee.power.variables;

public class DataTypes {

    public static void main(String[] args) {
        // Объявление переменных различных примитивных типов
        byte b = 10;
        short s = 200;
        int i = 1000;
        long l = 100000L;
        float f = 5.75f;
        double d = 19.99;
        char c = 'A';
        boolean bool = true;

        // Создание строк и массивов
        String str1 = "Привет";
        String str2 = "Java";
        String[] strArray = {"один", "два", "три"};
        int[] intArray = {1, 2, 3, 4, 5};

        // Математические операции с разными типами
        int sumInt = i + b;                   // int + byte -> int
        double sumDouble = d + f;             // double + float -> double
        long mulLong = l * i;                 // long * int -> long
        float divFloat = f / b;               // float / byte -> float
        int charToInt = c + 1;                // char + int -> int (символ A + 1 = B)

        // Преобразование типов
        int floatToInt = (int) f;             // приведение float к int (отрезание дробной части)
        double intToDouble = i;               // неявное преобразование int в double
        String intToString = Integer.toString(i); // int в строку
        int stringToInt = Integer.parseInt("123"); // строка в int

        // Вывод результатов
        System.out.println("byte b = " + b);
        System.out.println("short s = " + s);
        System.out.println("int i = " + i);
        System.out.println("long l = " + l);
        System.out.println("float f = " + f);
        System.out.println("double d = " + d);
        System.out.println("char c = '" + c + "'");
        System.out.println("boolean bool = " + bool);
        System.out.println();

        System.out.println("Строки: \"" + str1 + "\", \"" + str2 + "\"");
        System.out.print("Массив строк: ");
        for (String str : strArray) {
            System.out.print(str + " ");
        }
        System.out.println();

        System.out.print("Массив int: ");
        for (int val : intArray) {
            System.out.print(val + " ");
        }
        System.out.println("\n");

        System.out.println("Математические операции:");
        System.out.println("int + byte = " + i + " + " + b + " = " + sumInt);
        System.out.println("double + float = " + d + " + " + f + " = " + sumDouble);
        System.out.println("long * int = " + l + " * " + i + " = " + mulLong);
        System.out.println("float / byte = " + f + " / " + b + " = " + divFloat);
        System.out.println("char + int (символ 'A' + 1) = " + charToInt + " (код символа)");

        System.out.println();

        System.out.println("Преобразование типов:");
        System.out.println("float f = " + f + " -> int (с отбросом дробной части) = " + floatToInt);
        System.out.println("int i = " + i + " неявно преобразован в double = " + intToDouble);
        System.out.println("int i = " + i + " преобразован в String = \"" + intToString + "\"");
        System.out.println("String \"123\" преобразована в int = " + stringToInt);
    }

}
