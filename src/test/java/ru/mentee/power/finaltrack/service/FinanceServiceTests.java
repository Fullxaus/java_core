package ru.mentee.power.finaltrack.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.finaltrack.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceServiceTests {

    private FinanceService financeService;

    @BeforeEach
    void setUp() {
        financeService = new FinanceService();
    }

    // Тест на добавление транзакции
    @Test
    void testAddTransaction() {
        var tx = financeService.addTransaction(Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "");
        assertEquals(tx.getType(), Transaction.Type.INCOME);
        assertEquals(tx.getAmount(), new BigDecimal("1000"));
        assertEquals(tx.getCategory(), Transaction.Category.SALARY);
    }

    // Тест на просмотр всех транзакций
    @Test
    void testGetAllTransactions() {
        financeService.addTransaction(Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "");
        financeService.addTransaction(Transaction.Type.EXPENSE, new BigDecimal("500"), Transaction.Category.TRANSPORT, "");
        List<Transaction> transactions = financeService.getAllTransactions();
        assertEquals(transactions.size(), 2);
    }

    // Тест на фильтрацию транзакций по типу
    @Test
    void testFilterByType() {
        financeService.addTransaction(Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "");
        financeService.addTransaction(Transaction.Type.EXPENSE, new BigDecimal("500"), Transaction.Category.TRANSPORT, "");
        List<Transaction> incomes = financeService.getTransactionsByType(Transaction.Type.INCOME);
        assertEquals(incomes.size(), 1);
        assertTrue(incomes.get(0).getType() == Transaction.Type.INCOME);
    }

    // Тест на расчёт баланса
    @Test
    void testCalculateBalance() {
        financeService.addTransaction(Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "");
        financeService.addTransaction(Transaction.Type.EXPENSE, new BigDecimal("500"), Transaction.Category.TRANSPORT, "");
        BigDecimal balance = financeService.calculateBalance();
        assertEquals(balance, new BigDecimal("500"));
    }

    // Тест на расчёты итогов по категориям расходов
    @Test
    void testSummarizeExpensesByCategories() {
        financeService.addTransaction(Transaction.Type.EXPENSE, new BigDecimal("500"), Transaction.Category.TRANSPORT, "");
        financeService.addTransaction(Transaction.Type.EXPENSE, new BigDecimal("300"), Transaction.Category.FOOD, "");
        Map<Transaction.Category, BigDecimal> summary = financeService.summarizeExpensesByCategories();
        assertEquals(summary.get(Transaction.Category.TRANSPORT), new BigDecimal("500"));
        assertEquals(summary.get(Transaction.Category.FOOD), new BigDecimal("300"));
    }

    // Тест на удаление транзакции
    @Test
    void testDeleteTransaction() {
        var tx = financeService.addTransaction(Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "");
        financeService.deleteTransaction(tx.getId());
        List<Transaction> remainingTxs = financeService.getAllTransactions();
        assertTrue(remainingTxs.isEmpty());
    }
}
