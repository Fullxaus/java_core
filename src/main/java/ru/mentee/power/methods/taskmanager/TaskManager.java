package ru.mentee.power.methods.taskmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс для управления задачами
 */
public class TaskManager {
    private List<Task> tasks;
    private int nextId = 1;

    /**
     * Конструктор
     */
    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Добавление задачи с полным набором параметров
     */
    public Task addTask(String title, String description, Date dueDate, Task.Priority priority) {
        Task task = new Task(nextId++, title, description, dueDate, priority);
        tasks.add(task);
        return task;
    }

    /**
     * Добавление задачи только с названием (перегрузка)
     */
    public Task addTask(String title) {
        return addTask(title, "", null, Task.Priority.MEDIUM);
    }

    /**
     * Добавление задачи с названием и описанием (перегрузка)
     */
    public Task addTask(String title, String description) {
        return addTask(title, description, null, Task.Priority.MEDIUM);
    }

    /**
     * Получение задачи по ID
     */
    public Task getTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Удаление задачи по ID
     */
    public boolean removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Маркировка задачи как выполненной
     */
    public boolean markTaskAsCompleted(int id) {
        Task t = getTaskById(id);
        if (t != null) {
            t.markAsCompleted();
            return true;
        }
        return false;
    }

    /**
     * Получение всех задач
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Получение выполненных задач
     */
    public List<Task> getCompletedTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Получение невыполненных задач
     */
    public List<Task> getIncompleteTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (!t.isCompleted()) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Получение просроченных задач
     */
    public List<Task> getOverdueTasks() {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isOverdue()) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Получение задач с заданным приоритетом
     */
    public List<Task> getTasksByPriority(Task.Priority priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getPriority() == priority) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Поиск задач по фрагменту названия или описания
     */
    public List<Task> searchTasks(String query) {
        List<Task> result = new ArrayList<>();
        String lower = query == null ? "" : query.toLowerCase();
        for (Task t : tasks) {
            if (t.getTitle().toLowerCase().contains(lower)
                    || t.getDescription().toLowerCase().contains(lower)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Сортировка задач по сроку выполнения
     * Использует алгоритм сортировки пузырьком из блока циклов
     */
    public List<Task> sortTasksByDueDate() {
        List<Task> sorted = new ArrayList<>(tasks);
        int n = sorted.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Date d1 = sorted.get(j).getDueDate();
                Date d2 = sorted.get(j + 1).getDueDate();
                // null (без срока) ставим в конец
                if (d1 != null && d2 != null && d1.after(d2)
                        || d1 != null && d2 == null) {
                    Task tmp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, tmp);
                }
            }
        }
        return sorted;
    }

    /**
     * Сортировка задач по приоритету
     * Использует алгоритм сортировки вставками из блока циклов
     */
    public void sortTasksByPriority() {
        for (int i = 1; i < tasks.size(); i++) {
            Task key = tasks.get(i);
            int j = i - 1;
            while (j >= 0 && tasks.get(j).getPriority().ordinal() < key.getPriority().ordinal()) {
                tasks.set(j + 1, tasks.get(j));
                j--;
            }
            tasks.set(j + 1, key);
        }
    }

    /**
     * Вывод всех задач в консоль
     */
    public void printAllTasks() {
        printTasks(tasks, "=== Все задачи ===");
    }

    /**
     * Вывод задач с указанным заголовком
     */
    public void printTasks(List<Task> taskList, String header) {
        System.out.println(header);
        for (Task t : taskList) {
            System.out.println(t);
        }
        System.out.println();
    }
}
