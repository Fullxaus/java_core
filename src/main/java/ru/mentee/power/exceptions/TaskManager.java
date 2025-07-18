package ru.mentee.power.exceptions;

import org.jetbrains.annotations.NotNull;


/**
 * Класс, представляющий банковский счет.
 */
public class TaskManager {
    @NotNull
    private final String id;
    private double balance;

    public TaskManager(@NotNull String id, double initialBalance) {
        if (id == null) {
            throw new NullPointerException("id");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.id = id;
        this.balance = initialBalance;
    }

    /**
     * @return идентификатор счета, никогда не null
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * @return текущий баланс, может быть положительным или нулевым
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Пополнение счета.
     *
     * @param amount сумма пополнения, >= 0
     */
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма депозита не может быть отрицательной");
        }
        balance += amount;
    }

    /**
     * Снятие средств со счета.
     *
     * @param amount сумма снятия, >= 0
     * @throws TaskValidationException если amount > balance
     */
    public void withdraw(double amount) throws TaskValidationException {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма снятия не может быть отрицательной");
        }
        if (amount > balance) {
            double deficit = amount - balance;
            throw new TaskValidationException(
                    "Недостаточно средств для снятия",
                    balance, amount, deficit
            );
        }
        balance -= amount;
    }
}