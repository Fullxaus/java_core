package ru.mentee.power.collections.library;

import java.util.Objects;

public class Reader {
    private String id;
    private String name;
    private String email;
    private ReaderCategory category;

    public enum ReaderCategory {
        STUDENT, TEACHER, REGULAR, VIP
    }

    /**
     * Конструктор класса Reader.
     *
     * @param id       Уникальный идентификатор читателя
     * @param name     Имя читателя
     * @param email    Электронная почта читателя
     * @param category Категория читателя (STUDENT, TEACHER, REGULAR, VIP)
     */
    public Reader(String id, String name, String email, ReaderCategory category) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.category = category;
    }

    // Геттеры и сеттеры для всех полей

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ReaderCategory getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCategory(ReaderCategory category) {
        this.category = category;
    }

    /**
     * Переопределенный метод equals().
     * Читатели считаются равными, если у них одинаковый ID.
     *
     * @param o объект для сравнения
     * @return true если равны, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id);
    }

    /**
     * Переопределенный метод hashCode().
     *
     * @return hash код по id
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Переопределенный метод toString() для удобного вывода информации о классе.
     *
     * @return строковое представление объекта
     */
    @Override
    public String toString() {
        return "Reader{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", category=" + category +
                '}';
    }
}