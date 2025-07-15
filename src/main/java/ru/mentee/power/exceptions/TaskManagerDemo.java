package ru.mentee.power.exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Демонстрационное приложение для работы с TaskManager.
 */
public class TaskManagerDemo {

    public static void main(String[] args) {
        // Автоматическое закрытие Scanner
        try (Scanner scanner = new Scanner(System.in)) {

            TaskManager account = null;

            // Шаг 1: вводим строковый ID и начальный баланс
            while (account == null) {
                try {
                    System.out.print("Введите ID счёта (например, ABC123): ");
                    String id = scanner.next();         // единственное объявление id

                    System.out.print("Введите начальный баланс (неотрицательное число): ");
                    double balance = scanner.nextDouble();

                    account = new TaskManager(id, balance);
                }
                catch (InputMismatchException ime) {
                    System.out.println("Ошибка: введено не число. Повторите ввод.");
                    scanner.nextLine(); // очищаем ввод
                }
                catch (IllegalArgumentException iae) {
                    System.out.println("Ошибка: " + iae.getMessage());
                    scanner.nextLine(); // очищаем ввод
                }
            }

            // Шаг 2: основной цикл меню
            boolean exit = false;
            while (!exit) {
                printMenu();
                System.out.print("Выберите пункт меню: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                }
                catch (InputMismatchException ime) {
                    System.out.println("Ошибка: введите число от 1 до 4.");
                    scanner.nextLine();
                    continue;
                }

                switch (choice) {
                    case 1: // Внести деньги
                        try {
                            System.out.print("Сумма для внесения: ");
                            double deposit = scanner.nextDouble();
                            account.deposit(deposit);
                            System.out.printf("Успех! Новый баланс: %.2f%n", account.getBalance());
                        }
                        catch (InputMismatchException ime) {
                            System.out.println("Ошибка: введено не число.");
                            scanner.nextLine();
                        }
                        catch (IllegalArgumentException iae) {
                            System.out.println("Ошибка: " + iae.getMessage());
                        }
                        break;

                    case 2: // Снять деньги
                        try {
                            System.out.print("Сумма для снятия: ");
                            double withdraw = scanner.nextDouble();
                            account.withdraw(withdraw);
                            System.out.printf("Успех! Остаток на счёте: %.2f%n", account.getBalance());
                        }
                        catch (InputMismatchException ime) {
                            System.out.println("Ошибка: введите корректное число.");
                            scanner.nextLine();
                        }
                        catch (IllegalArgumentException iae) {
                            System.out.println("Ошибка: " + iae.getMessage());
                        }
                        catch (TaskValidationException tve) {
                            System.out.println("Недостаточно средств!");
                            System.out.println("Текущий баланс: " + tve.getBalance());
                            System.out.println("Запрошенная сумма: " + tve.getWithdrawAmount());
                            System.out.println("Не хватает: " + tve.getDeficit());
                        }
                        break;

                    case 3: // Проверить баланс
                        System.out.printf("Ваш текущий баланс: %.2f%n", account.getBalance());
                        break;

                    case 4: // Выход
                        exit = true;
                        System.out.println("Выход из программы...");
                        break;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите пункт от 1 до 4.");
                }
            }

        } // Scanner закрывается автоматически здесь

        System.out.println("Программа завершена. Спасибо за использование TaskManagerDemo.");
    }

    private static void printMenu() {
        System.out.println("====================================");
        System.out.println("            МЕНЮ TASK MANAGER       ");
        System.out.println("====================================");
        System.out.println("1. Внести деньги");
        System.out.println("2. Снять деньги");
        System.out.println("3. Проверить баланс");
        System.out.println("4. Выход");
        System.out.println("====================================");
    }
}
