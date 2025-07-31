package ru.mentee.power.methods;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ReferenceParameterDemo {
    private static final Logger logger = Logger.getLogger(ReferenceParameterDemo.class.getName());

    public static void main(String[] args) {
        // вместо ArrayList<String> — интерфейс List<String>
        List<String> myList = new ArrayList<>();
        myList.add("Первый элемент");

        logger.info("До вызова метода: myList = " + myList);
        logger.info("До вызова метода: размер списка = " + myList.size());

        addItem(myList);

        logger.info("После вызова метода: myList = " + myList);
        logger.info("После вызова метода: размер списка = " + myList.size());
    }

    // параметр тоже объявляем через интерфейс List, а не конкретный ArrayList
    public static void addItem(List<String> list) {
        logger.info("В начале метода: list = " + list);
        list.add("Новый элемент");
        logger.info("После изменения в методе: list = " + list);
    }
}