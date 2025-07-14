package ru.mentee.power.methods;

public class DivisionResult {
    public int quotient;
    public int remainder;


    public DivisionResult divide(int dividend, int divisor) {
        DivisionResult result = new DivisionResult();
        result.quotient = dividend / divisor;
        result.remainder = dividend % divisor;
        return result;
    }

    public static void main(String[] args) {

        DivisionResult divisionResult =new DivisionResult();
        DivisionResult result =divisionResult.divide(6,3);

        System.out.println("Quotient (частное): " + result.quotient);
        System.out.println("Remainder (остаток): " + result.remainder);



    }
}
