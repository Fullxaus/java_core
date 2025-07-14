package ru.mentee.power.methods;

import java.util.ArrayList;

public class ReferenceParameterDemo {
    public static void main(String[] args) {
        ArrayList<String> myList = new ArrayList<>();
        myList.add("Первый элемент");

        System.out.println("До вызова метода: myList = " + myList);
        System.out.println("До вызова метода: размер списка = " + myList.size());

        addItem(myList);

        System.out.println("После вызова метода: myList = " + myList);
        System.out.println("После вызова метода: размер списка = " + myList.size());
    }

    public static void addItem(ArrayList<String> list) {
        System.out.println("В начале метода: list = " + list);
        list.add("Новый элемент");
        System.out.println("После изменения в методе: list = " + list);
    }
}