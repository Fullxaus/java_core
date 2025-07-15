package ru.mentee.power.exceptions;

/**
 * Класс, представляющий банковский счет.
 */
public class TaskManager {
    private final String id;
    private double balance;

    public TaskManager(String id, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма депозита не может быть отрицательной");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws TaskValidationException {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма снятия не может быть отрицательной");
        }
        if (amount > balance) {
            double deficit = amount - balance;
            throw new TaskValidationException(
                    "Недостаточно средств для снятия",
                    balance, amount, deficit);
        }
        balance -= amount;
    }
}
