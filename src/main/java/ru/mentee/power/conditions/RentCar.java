package ru.mentee.power.conditions;

import java.util.Scanner;

public class RentCar {

    public static int age;

    public static void age(){

        Scanner console=new Scanner(System.in);
        System.out.println("ведите возраст человека");
        age= console.nextInt();
        console.close();
    }

    public static void rentCar(){


        if(age>=18){
            System.out.println("Вы можете арендовать автомобиль");
        }else {
            System.out.println("Вы не можете арендовать автомобиль");
        }

    }

    public static void rentCar2(){


       String answer= age>=18?"Вы можете арендовать автомобиль":"Вы не можете арендовать автомобиль";

        System.out.println(answer);
    }

    public static void main(String[] args) {

        age();
        rentCar();
        rentCar2();


    }


}
