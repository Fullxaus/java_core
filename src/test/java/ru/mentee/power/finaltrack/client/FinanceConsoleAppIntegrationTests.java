package ru.mentee.power.finaltrack.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mentee.power.finaltrack.model.Transaction;
import ru.mentee.power.finaltrack.service.FinanceService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceConsoleAppIntegrationTests {

    private FinanceService financeService;
    private FinanceConsoleApp consoleApp;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        financeService = new FinanceService();
        scanner = new Scanner(System.in); // или DummyScanner, если нужно
        consoleApp = new FinanceConsoleApp(financeService, scanner);
    }


    @Test
    void testAddTransaction() {
        // Создаем экземпляр FinanceService
        FinanceService financeService = new FinanceService();

        // Добавляем транзакцию
        financeService.addTransaction(Transaction.Type.INCOME, new BigDecimal("1000"), Transaction.Category.SALARY, "Моя зарплата");

        // Получаем список транзакций
        List<Transaction> transactions = financeService.getAllTransactions();
        System.out.println("Транзакции: " + transactions);

        assertFalse(transactions.isEmpty(), "Ожидалось, что список транзакций не пуст");
        assertEquals(1, transactions.size(), "Ожидалось ровно одна транзакция");
        assertEquals(new BigDecimal("1000"), transactions.get(0).getAmount(), "Ожидалась сумма транзакции 1000");
        assertEquals(Transaction.Category.SALARY, transactions.get(0).getCategory(), "Ожидалась категория транзакции SALARY");
    }
    @Test
    void testCalculateBalance() {
        consoleApp.processCommand("add income 1000 SALARY Моя зарплата");
        consoleApp.processCommand("add expense 500 TRANSPORT Проезд на автобусе");

        BigDecimal balance = financeService.calculateBalance();
        assertEquals(new BigDecimal("500"), balance);
    }

    // Тест на интеграцию команды вывода всех транзакций
    @Test
    void testListAllTransactions() {
        consoleApp.processCommand("add income 1000 SALARY Моя зарплата");
        consoleApp.processCommand("add expense 500 TRANSPORT Проезд на автобусе");
        List<Transaction> transactions = financeService.getAllTransactions();
        assertEquals(transactions.size(), 2);
    }

    // Тест на интеграцию команды вывода итогов по категориям
    @Test
    void testSummarizeExpensesByCategories() {
        consoleApp.processCommand("add expense 500 TRANSPORT Проезд на автобусе");
        consoleApp.processCommand("add expense 300 FOOD Обед в ресторане");
        var summary = financeService.summarizeExpensesByCategories();
        assertEquals(summary.get(Transaction.Category.TRANSPORT), new BigDecimal("500"));
        assertEquals(summary.get(Transaction.Category.FOOD), new BigDecimal("300"));
    }
}
