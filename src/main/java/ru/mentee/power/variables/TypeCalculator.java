package ru.mentee.power.variables;


import java.util.Scanner;

public class TypeCalculator {



    public static void addByteShort(){

        Scanner console = new Scanner(System.in);
        System.out.println("переменная byte");
       byte a= console.nextByte();
        System.out.println("переменная short");
       short b= console.nextShort();
       int z=a+b;
        System.out.println("Сложение переменных a+b "+z);
    }

    public static void multiplyIntByte(){

        Scanner console = new Scanner(System.in);
        System.out.println("переменная int");
        int c= console.nextInt();
        System.out.println("переменная byte");
        byte d= console.nextByte();
        int e=c*d;
        System.out.println("Умножение переменных c*d "+e);

    }

    public static void addDoubleInt(){

        Scanner console = new Scanner(System.in);
        System.out.println("переменная double");
        double c= console.nextDouble();
        System.out.println("переменная byte");
        byte d= console.nextByte();
        double e=c+d;
        System.out.println("Умножение переменных c*d "+e);

    }

    public static void divideIntInt(){

        Scanner console = new Scanner(System.in);
        System.out.println("переменная int");
        int a= console.nextInt();
        System.out.println("переменная int");
        int b = console.nextInt();
        int c = a/b;
        System.out.println("Деление  a/b "+c);

    }

    public static void divideDoubleInt(){

        Scanner console = new Scanner(System.in);
        System.out.println("переменная double");
        double a= console.nextDouble();
        System.out.println("переменная int");
        int b= console.nextInt();
        double c= a/b;

        System.out.println("Деление a/b"+c);


    }

    public static void chouseParametr(){

        System.out.println("Выберите цифру :1,2,3,4,5");
        System.out.println("1=addByteShort");
        System.out.println("2=multiplyIntByte");
        System.out.println("3=addDoubleInt");
        System.out.println("4=divideIntInt");
        System.out.println("5=divideDoubleInt");
        Scanner console = new Scanner(System.in);
        int a= console.nextInt();
        if(a==1){
            addByteShort();
        } else if (a==2) {
            multiplyIntByte();
        } else if (a==3) {
            addDoubleInt();
        } else if (a==4) {
            divideIntInt();
        } else if (a==5) {
            divideDoubleInt();
        }


    }

    public static void main(String[] args) {

        chouseParametr();


    }
}
