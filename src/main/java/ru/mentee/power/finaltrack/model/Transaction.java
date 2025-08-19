package ru.mentee.power.finaltrack.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private final int id;
    private final Type type;
    private final BigDecimal amount;
    private final LocalDate date;
    private final Category category;
    private final String description;

    public Transaction(int id, Type type, BigDecimal amount, Category category, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = LocalDate.now();
        this.category = category;
        this.description = description;
    }

    // Getters для полей
    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    // Enums для удобства классификации
    public enum Type {
        INCOME, EXPENSE
    }

    public enum Category {
        SALARY, FOOD, TRANSPORT, ENTERTAINMENT, OTHER
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}