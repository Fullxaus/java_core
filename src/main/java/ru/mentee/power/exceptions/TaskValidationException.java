package ru.mentee.power.exceptions;

/**
 * Собственное проверяемое исключение для ситуации нехватки средств.
 */
public class TaskValidationException extends Exception {
    private final double balance;
    private final double withdrawAmount;
    private final double deficit;

    public TaskValidationException(String message,
                                   double balance,
                                   double withdrawAmount,
                                   double deficit) {
        super(message);
        this.balance = balance;
        this.withdrawAmount = withdrawAmount;
        this.deficit = deficit;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public double getDeficit() {
        return deficit;
    }
}
