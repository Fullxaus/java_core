package ru.mentee.power.variables;

import java.util.Scanner;

public class PersonalCard {

    public static String name;
    public static String surname;
    public static int age;
    public static String town;
    public static int height;
    public static int weight;
    public static boolean student;
    public static String firstTypeName;

    public static void personalCard() {
        Scanner console = new Scanner(System.in);

        System.out.println("Введите имя");
        name = console.nextLine();

        System.out.println("Введите фамилию");
        surname = console.nextLine();

        System.out.println("Введите возраст");
        age = console.nextInt();
        console.nextLine(); // очистка буфера после nextInt

        System.out.println("Введите город");
        town = console.nextLine();

        System.out.println("Введите рост");
        height = console.nextInt();
        console.nextLine(); // очистка буфера

        System.out.println("Введите вес");
        weight = console.nextInt();
        console.nextLine(); // очистка буфера

        System.out.println("Является ли студентом (true/false)");
        student = console.nextBoolean();
        console.nextLine(); // очистка буфера

        System.out.println("Введите первую букву имени");
        firstTypeName = console.nextLine();

        System.out.println();
        System.out.println(name + " " + surname + " " + age + " " + town + " " + height + " " + weight + " " + student + " " + firstTypeName);
    }

    public static void main(String[] args) {

        personalCard();


    }


}
