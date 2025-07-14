package ru.mentee.power.methods.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Класс, представляющий задачу
 */
public class Task {
    private int id;             // Уникальный идентификатор
    private String title;       // Название задачи
    private String description; // Описание задачи
    private Date dueDate;       // Срок выполнения
    private Priority priority;  // Приоритет
    private boolean completed;  // Статус выполнения

    /**
     * Приоритет задачи
     */
    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    /**
     * Конструктор с полным набором параметров
     */
    public Task(int id, String title, String description, Date dueDate, Priority priority) {
        this.id = id;
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.description = description != null ? description : "";
        this.dueDate = dueDate != null ? new Date(dueDate.getTime()) : null;
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.completed = false;
    }

    /**
     * Конструктор с минимальным набором параметров (перегрузка)
     */
    public Task(int id, String title) {
        this(id, title, "", null, Priority.MEDIUM);
    }

    /**
     * Конструктор с частичным набором параметров (перегрузка)
     */
    public Task(int id, String title, String description) {
        this(id, title, description, null, Priority.MEDIUM);
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "title must not be null");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public Date getDueDate() {
        return dueDate != null ? new Date(dueDate.getTime()) : null;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = (dueDate != null) ? new Date(dueDate.getTime()) : null;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Метод для маркировки задачи как выполненной
     */
    public void markAsCompleted() {
        this.completed = true;
    }

    /**
     * Метод для проверки, просрочена ли задача
     */
    public boolean isOverdue() {
        if (dueDate == null) {
            return false;
        }
        Date now = new Date();
        return !completed && dueDate.before(now);
    }

    /**
     * Переопределение метода toString для удобного отображения задачи
     */
    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String due = dueDate == null ? "none" : fmt.format(dueDate);
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + due +
                ", priority=" + priority +
                ", completed=" + completed +
                '}';
    }
}
