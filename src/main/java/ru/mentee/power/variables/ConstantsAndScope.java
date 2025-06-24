// Константы класса (static final)
public static final double PI = 3.14159;
public static final String APP_NAME = "MyApplication";

public static void main(String[] args) {
    // Локальные переменные и локальные константы
    int localVar = 10;
    final int LOCAL_CONST = 20;

    {
        // Переменные, видимые только внутри этого блока
        int blockVar = 30;
        final int BLOCK_CONST = 40;
        System.out.println("Внутри блока:");
        System.out.println("blockVar = " + blockVar);         // Работает
        System.out.println("BLOCK_CONST = " + BLOCK_CONST);   // Работает
        System.out.println("localVar = " + localVar);         // Работает
        System.out.println("LOCAL_CONST = " + LOCAL_CONST);   // Работает
        System.out.println("PI = " + PI);                     // Работает
    }

    // Вне блока
    System.out.println("Вне блока:");
    System.out.println("localVar = " + localVar);             // Работает
    System.out.println("LOCAL_CONST = " + LOCAL_CONST);       // Работает
    System.out.println("PI = " + PI);                         // Работает

    // Следующие строки вызовут ошибку, т.к. переменные объявлены внутри блока и не видимы вне его
    // System.out.println("blockVar = " + blockVar);         // Ошибка: cannot find symbol
    // System.out.println("BLOCK_CONST = " + BLOCK_CONST);   // Ошибка: cannot find symbol

    // Константы класса доступны везде, так как они static и final
    System.out.println("APP_NAME = " + APP_NAME);             // Работает
}

