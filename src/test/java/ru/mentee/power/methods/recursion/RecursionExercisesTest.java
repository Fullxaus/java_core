package ru.mentee.power.methods.recursion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;



    public class RecursionExercisesTest {

        @Test
        public void testFactorial() {
            assertThat(RecursionExercises.factorial(0)).isEqualTo(1);
            assertThat(RecursionExercises.factorial(1)).isEqualTo(1);
            assertThat(RecursionExercises.factorial(2)).isEqualTo(2);
            assertThat(RecursionExercises.factorial(3)).isEqualTo(6);
            assertThat(RecursionExercises.factorial(5)).isEqualTo(120);
            assertThat(RecursionExercises.factorial(10)).isEqualTo(3628800);

            // вместо assertThat(...).isEqualTo(-1);
            assertThatThrownBy(() -> RecursionExercises.factorial(-1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("n must be >= 0");
        }

        @Test
        public void testFibonacci() {
            assertThat(RecursionExercises.fibonacci(0)).isEqualTo(0);
            assertThat(RecursionExercises.fibonacci(1)).isEqualTo(1);
            assertThat(RecursionExercises.fibonacci(2)).isEqualTo(1);
            assertThat(RecursionExercises.fibonacci(3)).isEqualTo(2);
            assertThat(RecursionExercises.fibonacci(4)).isEqualTo(3);
            assertThat(RecursionExercises.fibonacci(5)).isEqualTo(5);
            assertThat(RecursionExercises.fibonacci(6)).isEqualTo(8);
            assertThat(RecursionExercises.fibonacci(10)).isEqualTo(55);

            assertThatThrownBy(() -> RecursionExercises.fibonacci(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void testFibonacciOptimized() {
            assertThat(RecursionExercises.fibonacciOptimized(0)).isEqualTo(0);
            assertThat(RecursionExercises.fibonacciOptimized(1)).isEqualTo(1);
            assertThat(RecursionExercises.fibonacciOptimized(2)).isEqualTo(1);
            assertThat(RecursionExercises.fibonacciOptimized(3)).isEqualTo(2);
            assertThat(RecursionExercises.fibonacciOptimized(4)).isEqualTo(3);
            assertThat(RecursionExercises.fibonacciOptimized(5)).isEqualTo(5);
            assertThat(RecursionExercises.fibonacciOptimized(6)).isEqualTo(8);
            assertThat(RecursionExercises.fibonacciOptimized(10)).isEqualTo(55);
            // Проверяем, что метод может эффективно вычислять большие числа Фибоначчи
            assertThat(RecursionExercises.fibonacciOptimized(20)).isEqualTo(6765);
            assertThat(RecursionExercises.fibonacciOptimized(30)).isEqualTo(832040);

            // Вместо проверки на -1 ожидаем исключение при n < 0
            assertThatThrownBy(() -> RecursionExercises.fibonacciOptimized(-10))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("n must be >= 0");
        }

        @Test
        public void testIsPalindrome() {
            // Пустая строка — палиндром
            assertThat(RecursionExercises.isPalindrome("")).isTrue();

            // Три «чистых» примера палиндромов (без пробелов и с одинаковым регистром)
            assertThat(RecursionExercises.isPalindrome("madam")).isTrue();
            assertThat(RecursionExercises.isPalindrome("racecar")).isTrue();
            assertThat(RecursionExercises.isPalindrome("level")).isTrue();

            // Три примера НЕ-палиндромов
            assertThat(RecursionExercises.isPalindrome("hello")).isFalse();
            assertThat(RecursionExercises.isPalindrome("world")).isFalse();
            assertThat(RecursionExercises.isPalindrome("java")).isFalse();
        }

        @Test
        public void testSumOfDigits() {
            assertThat(RecursionExercises.sumOfDigits(0)).isEqualTo(0);
            assertThat(RecursionExercises.sumOfDigits(5)).isEqualTo(5);
            assertThat(RecursionExercises.sumOfDigits(9)).isEqualTo(9);
            assertThat(RecursionExercises.sumOfDigits(123)).isEqualTo(6);
            assertThat(RecursionExercises.sumOfDigits(1234)).isEqualTo(10);
            assertThat(RecursionExercises.sumOfDigits(12345)).isEqualTo(15);
        }

        @Test
        public void testPower() {
            assertThat(RecursionExercises.power(2, 0)).isCloseTo(1, within(0.0001));
            assertThat(RecursionExercises.power(2, 1)).isCloseTo(2, within(0.0001));
            assertThat(RecursionExercises.power(2, 2)).isCloseTo(4, within(0.0001));
            assertThat(RecursionExercises.power(2, 3)).isCloseTo(8, within(0.0001));
            assertThat(RecursionExercises.power(2, 4)).isCloseTo(16, within(0.0001));
            assertThat(RecursionExercises.power(5, 0)).isCloseTo(1, within(0.0001));
            assertThat(RecursionExercises.power(5, 1)).isCloseTo(5, within(0.0001));
            assertThat(RecursionExercises.power(5, 2)).isCloseTo(25, within(0.0001));

            // Проверяем отрицательные показатели
            assertThat(RecursionExercises.power(2, -1)).isCloseTo(0.5, within(0.0001));
            assertThat(RecursionExercises.power(2, -2)).isCloseTo(0.25, within(0.0001));
            assertThat(RecursionExercises.power(5, -1)).isCloseTo(0.2, within(0.0001));
            assertThat(RecursionExercises.power(5, -2)).isCloseTo(0.04, within(0.0001));
        }

        @Test
        public void testGcd() {
            assertThat(RecursionExercises.gcd(1, 1)).isEqualTo(1);
            assertThat(RecursionExercises.gcd(2, 3)).isEqualTo(1);
            assertThat(RecursionExercises.gcd(2, 4)).isEqualTo(2);
            assertThat(RecursionExercises.gcd(4, 6)).isEqualTo(2);
            assertThat(RecursionExercises.gcd(3, 9)).isEqualTo(3);
            assertThat(RecursionExercises.gcd(12, 18)).isEqualTo(6);
            assertThat(RecursionExercises.gcd(17, 13)).isEqualTo(1);
            assertThat(RecursionExercises.gcd(36, 48)).isEqualTo(12);
        }

        @Test
        public void testReverseArray() {
            int[] array1 = {1};
            RecursionExercises.reverseArray(array1, 0, array1.length - 1);
            assertThat(array1).containsExactly(1);

            int[] array2 = {1, 2};
            RecursionExercises.reverseArray(array2, 0, array2.length - 1);
            assertThat(array2).containsExactly(2, 1);

            int[] array3 = {1, 2, 3};
            RecursionExercises.reverseArray(array3, 0, array3.length - 1);
            assertThat(array3).containsExactly(3, 2, 1);

            int[] array4 = {1, 2, 3, 4, 5};
            RecursionExercises.reverseArray(array4, 0, array4.length - 1);
            assertThat(array4).containsExactly(5, 4, 3, 2, 1);

            // Проверка частичного обращения
            int[] array5 = {1, 2, 3, 4, 5};
            RecursionExercises.reverseArray(array5, 1, 3);
            assertThat(array5).containsExactly(1, 4, 3, 2, 5);
        }

    }

