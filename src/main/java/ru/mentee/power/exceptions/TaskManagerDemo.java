package ru.mentee.power.exceptions;

import ru.mentee.power.methods.taskmanager.Task;
import ru.mentee.power.methods.taskmanager.TaskManager;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Демонстрационное приложение для работы с TaskManager (из MP-44),
 * которое улучшено с помощью обработки исключений.
 */
public class TaskManagerDemo {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        initializeTasks();

        boolean running = true;
        System.out.println("Добро пожаловать в Менеджер Задач (с расширенной обработкой ошибок)!");

        while (running) {
            printMenu();

            int choice;
            // TODO 1: Обработать InputMismatchException при вводе выбора меню
            try {
                System.out.print("Выберите действие (1-5): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Поглотить перевод строки
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода: пожалуйста, введите номер действия (целое число 1–5).");
                scanner.nextLine(); // Очистить буфер
                continue;
            }

            switch (choice) {
                case 1:
                    addTaskUI();
                    break;
                case 2:
                    markTaskAsCompletedUI();
                    break;
                case 3:
                    removeTaskUI();
                    break;
                case 4:
                    taskManager.printAllTasks();
                    break;
                case 5:
                    running = false;
                    System.out.println("Выход из программы.");
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите от 1 до 5.");
            }
            System.out.println();
        }

        scanner.close();
    }

    // --- UI methods ---

    private static void addTaskUI() {
        try {
            System.out.print("Введите название задачи: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Название задачи не может быть пустым.");
            }

            Task newTask = taskManager.addTask(title);
            if (newTask != null) {
                System.out.println("Задача '" + newTask.getTitle() +
                        "' (ID: " + newTask.getId() + ") успешно добавлена.");
            } else {
                System.out.println("Не удалось добавить задачу.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка при добавлении задачи: " + e.getMessage());
        }
    }

    private static void markTaskAsCompletedUI() {
        try {
            System.out.print("Введите ID задачи для отметки как выполненной: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            // TODO 2: проверка на отрицательный или нулевой ID
            if (id <= 0) {
                throw new IllegalArgumentException("ID задачи должен быть положительным числом.");
            }

            // Симулируем потенциальный NullPointerException
            // Task t = taskManager.getTaskById(id);
            // t.setCompleted(true);

            boolean success = taskManager.markTaskAsCompleted(id);
            if (success) {
                System.out.println("Задача с ID " + id + " отмечена как выполненная.");
            } else {
                System.out.println("Задача с ID " + id + " не найдена.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: пожалуйста, введите корректный ID (целое число).");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            // TODO 3: перехват NullPointerException
        } catch (NullPointerException e) {
            System.out.println("Критическая ошибка: попытка работы с несуществующей задачей.");
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка при отметке задачи: " + e.getMessage());
        }
    }

    private static void removeTaskUI() {
        try {
            System.out.print("Введите ID задачи для удаления: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            // TODO 4: проверка на отрицательный или нулевой ID
            if (id <= 0) {
                throw new IllegalArgumentException("ID задачи должен быть положительным числом.");
            }

            boolean success = taskManager.removeTask(id);
            if (success) {
                System.out.println("Задача с ID " + id + " удалена.");
            } else {
                System.out.println("Задача с ID " + id + " не найдена.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: пожалуйста, введите корректный ID (целое число).");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка при удалении задачи: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("===== МЕНЮ =====");
        System.out.println("1. Добавить задачу (только название)");
        System.out.println("2. Отметить задачу как выполненную");
        System.out.println("3. Удалить задачу");
        System.out.println("4. Показать все задачи");
        System.out.println("5. Выход");
        System.out.println("===============");
    }

    private static void initializeTasks() {
        taskManager.addTask("Изучить исключения");
        taskManager.addTask("Попрактиковаться с TaskManager");
        System.out.println("Добавлены начальные задачи:");
        taskManager.printAllTasks();
        System.out.println();
    }
}
