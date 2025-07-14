package ru.mentee.power.methods;

public class PrimitiveParameterDemo {
    public static void main(String[] args) {
        int number = 5;
        System.out.println("До вызова метода: number = " + number);

        increment(number);

        System.out.println("После вызова метода: number = " + number);
    }

    public static void increment(int x) {
        System.out.println("В начале метода: x = " + x);
        x++;
        System.out.println("После изменения в методе: x = " + x);
    }
}
