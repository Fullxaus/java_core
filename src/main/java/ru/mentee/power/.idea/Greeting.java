package ru.mentee.power;
&nbsp;

public class Greeting{
    public static String getGreeting(String name){
        return "Привет, " + name + "!";
    }
    public static void main(String[] args){
        System.out.println(getGreeting("Java разработчик"));
    }
}