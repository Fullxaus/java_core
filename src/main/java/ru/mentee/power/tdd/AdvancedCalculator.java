package ru.mentee.power.tdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Суммирует числа в списке, игнорируя значения > 1000.
 */
public class AdvancedCalculator {

    public int sumIgnoringOver1000(List<Integer> numbers) {
        if (numbers == null) {
            return 0;
        }

        int sum = 0;
        for (int number : numbers) {
            //  проверяем число <= 1000
            if (number <= 1000) {
                sum += number;
            }
        }
        return sum;
    }
    }


