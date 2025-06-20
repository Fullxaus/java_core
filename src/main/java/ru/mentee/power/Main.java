package ru.mentee.power;

import ru.mentee.power.variables.Variables;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Variables variables=new Variables(30,"Dmitrii");

        variables.getName();
        variables.getAge();

        System.out.println("My name "+variables.getName());
        System.out.println("I am "+variables.getAge()+"years old");

    }
    }



