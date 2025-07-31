package ru.mentee.power.loop;

import java.util.logging.Logger;

public class FizzBuzz {

    // Логгер для вывода сообщений
    private static final Logger logger =
            Logger.getLogger(FizzBuzz.class.getName());

    /**
     * Генерирует массив FizzBuzz длиной n
     *
     * @param n верхняя граница (числа от 1 до n)
     * @return массив строк с результатами FizzBuzz
     */
    public String[] generateFizzBuzz(int n) {
        String[] result = new String[n];

        for (int index = 1; index <= n; index++) {
            if (index % 3 == 0 && index % 5 == 0) {
                result[index - 1] = "FizzBuzz";
            } else if (index % 3 == 0) {
                result[index - 1] = "Fizz";
            } else if (index % 5 == 0) {
                result[index - 1] = "Buzz";
            } else {
                result[index - 1] = String.valueOf(index);
            }
        }

        return result;
    }

    /**
     * Печатает результаты FizzBuzz в лог
     *
     * @param n верхняя граница (числа от 1 до n)
     */
    public void printFizzBuzz(int n) {
        String[] results = generateFizzBuzz(n);
        for (String result : results) {
            logger.info(result);
        }
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz();

        logger.info("FizzBuzz для чисел от 1 до 30:");
        fizzBuzz.printFizzBuzz(30);
    }
}

