package ru.mentee.power.loop;

public class ForEachLoopExample {
    public static void main(String[] args) {
        String[] fruits = {"Яблоко", "Банан", "Апельсин"};

        System.out.println("Фрукты (без индекса):");
        // Перебираем массив с помощью for-each
        for (String fruit : fruits) {
            System.out.println(fruit);
        }
    }
}
