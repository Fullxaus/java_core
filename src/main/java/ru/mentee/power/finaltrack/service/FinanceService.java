package ru.mentee.power.finaltrack.service;

import ru.mentee.power.finaltrack.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FinanceService {
    private final AtomicInteger transactionCounter = new AtomicInteger(0);
    private final List<Transaction> transactions = new ArrayList<>();

    // Добавление транзакции
    public Transaction addTransaction(Transaction.Type type, BigDecimal amount, Transaction.Category category, String description) {
        validate(amount, category);
        var nextId = transactionCounter.incrementAndGet();
        var newTransaction = new Transaction(nextId, type, amount, category, description);
        transactions.add(newTransaction);
        return newTransaction;
    }

    // Получение всех транзакций
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    // Получение транзакций по типу
    public List<Transaction> getTransactionsByType(Transaction.Type type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .collect(Collectors.toList());
    }

    // Баланс пользователя
    public BigDecimal calculateBalance() {
        BigDecimal income = transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Общий доход: " + income);

        BigDecimal expense = transactions.stream()
                .filter(t -> t.getType() == Transaction.Type.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Общие расходы: " + expense);

        return income.subtract(expense);
    }
    // Итоги по категориям расходов
    public Map<Transaction.Category, BigDecimal> summarizeExpensesByCategories() {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == Transaction.Type.EXPENSE)
                .collect(Collectors.groupingBy(Transaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));
    }

    // Частная функция для подсчета суммы транзакций конкретного типа
    private BigDecimal getTotalSumForType(Transaction.Type type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType() == type)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Метод для удаления транзакции по её ID
    public boolean deleteTransaction(int transactionId) {
        Optional<Transaction> foundTx = transactions.stream()
                .filter(tx -> tx.getId() == transactionId)
                .findAny();

        if (foundTx.isPresent()) {
            transactions.remove(foundTx.get());
            return true;
        }
        return false;
    }

    // Валидатор суммы и категории
    private void validate(BigDecimal amount, Transaction.Category category) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительным числом.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Категория не указана.");
        }
    }
}