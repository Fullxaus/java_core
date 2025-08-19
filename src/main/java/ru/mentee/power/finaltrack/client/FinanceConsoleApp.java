package ru.mentee.power.finaltrack.client;

import ru.mentee.power.finaltrack.model.Transaction;
import ru.mentee.power.finaltrack.service.FinanceService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FinanceConsoleApp {
    private final FinanceService service;
    private final Scanner scanner;

    // Конструктор для внедрения зависимостей (используется в тестах)
    public FinanceConsoleApp(FinanceService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    // Конструктор по умолчанию для запуска из main()
    public FinanceConsoleApp() {
        this(new FinanceService(), new Scanner(System.in));
    }

    public void start() {
        showWelcomeMessage();
        runLoop();
    }

    private void showWelcomeMessage() {
        System.out.println("Добро пожаловать в CoinKeeper!");
        System.out.println("Список доступных команд:");
        System.out.println("add income <сумма> <категория> [описание]");
        System.out.println("add expense <сумма> <категория> [описание]");
        System.out.println("list all");
        System.out.println("list income");
        System.out.println("list expense");
        System.out.println("balance");
        System.out.println("summary");
        System.out.println("exit");
    }

    private void runLoop() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            processCommand(input);
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
        }
    }

    void processCommand(String input) {
        if (input.isBlank()) {
            System.out.println("Введите команду.");
            return;
        }
        String[] tokens = input.split("\\s+");
        switch (tokens[0].toLowerCase()) {
            case "add":
                handleAddCommand(tokens);
                break;
            case "list":
                handleListCommand(tokens);
                break;
            case "balance":
                handleBalanceCommand();
                break;
            case "summary":
                handleSummaryCommand();
                break;
            case "exit":
                System.out.println("До свидания!");
                break;
            default:
                System.out.println("Команда не распознана. Попробуйте еще раз.");
        }
    }

    private void handleAddCommand(String[] tokens) {
        if (tokens.length < 4) {
            System.out.println("Недостаточно аргументов. Формат: add <income|expense> <сумма> <категория> [описание]");
            return;
        }

        // 1) Тип транзакции
        Transaction.Type type;
        switch (tokens[1].toLowerCase()) {
            case "income":
                type = Transaction.Type.INCOME;
                break;
            case "expense":
                type = Transaction.Type.EXPENSE;
                break;
            default:
                System.out.println("Неизвестный тип транзакции: " + tokens[1]);
                return;
        }

        // 2) Сумма
        BigDecimal amount;
        try {
            amount = new BigDecimal(tokens[2].replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("Неверная сумма: " + tokens[2]);
            return;
        }

        // 3) Категория
        Transaction.Category category;
        try {
            category = Transaction.Category.valueOf(tokens[3].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Неизвестная категория: " + tokens[3]);
            return;
        }

        // 4) Описание — всё, что осталось после 4-го токена
        String description = "";
        if (tokens.length > 4) {
            description = String.join(" ", Arrays.copyOfRange(tokens, 4, tokens.length));
        }

        // 5) Добавление транзакции
        try {
            service.addTransaction(type, amount, category, description);
            System.out.println("Транзакция успешно добавлена!");
        } catch (IllegalArgumentException ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }

    private void handleListCommand(String[] tokens) {
        if (tokens.length != 2) {
            System.out.println("Некорректный формат команды. Формат: list <all|income|expense>");
            return;
        }

        List<Transaction> list;
        switch (tokens[1].toLowerCase()) {
            case "all":
                list = service.getAllTransactions();
                break;
            case "income":
                list = service.getTransactionsByType(Transaction.Type.INCOME);
                break;
            case "expense":
                list = service.getTransactionsByType(Transaction.Type.EXPENSE);
                break;
            default:
                System.out.println("Неизвестный аргумент команды list: " + tokens[1]);
                return;
        }
        displayTransactions(list);
    }

    private void handleBalanceCommand() {
        BigDecimal balance = service.calculateBalance();
        System.out.println("Ваш текущий баланс: " + balance + " рублей");
    }

    private void handleSummaryCommand() {
        var summary = service.summarizeExpensesByCategories();
        if (summary.isEmpty()) {
            System.out.println("Нет расходов для суммирования.");
            return;
        }
        System.out.println("Итоги по категориям расходов:");
        summary.forEach((cat, sum) ->
                System.out.println(cat.name() + ": " + sum + " рублей")
        );
    }

    private void displayTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("Нет транзакций.");
            return;
        }
        System.out.println("Список транзакций:");
        transactions.forEach(System.out::println);
    }

    public static void main(String[] args) {
        new FinanceConsoleApp().start();
    }
}
