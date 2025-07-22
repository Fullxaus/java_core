package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.Objects;

public class Borrowing {
    private String isbn;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    /**
     * Конструктор класса Borrowing.
     *
     * @param isbn        ISBN книги
     * @param readerId    ID читателя
     * @param borrowDate  Дата выдачи книги
     * @param dueDate     Срок возврата книги
     */
    public Borrowing(String isbn, String readerId, LocalDate borrowDate, LocalDate dueDate) {
        this.isbn = isbn;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    // Геттеры и сеттеры для всех полей

    public String getIsbn() {
        return isbn;
    }

    public String getReaderId() {
        return readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Проверка, является ли запись о заимствовании просроченной.
     *
     * @return true, если книга просрочена и не возвращена
     */
    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    /**
     * Проверка, была ли книга возвращена.
     *
     * @return true, если книга была возвращена
     */
    public boolean isReturned() {
        return returnDate != null;
    }

    /**
     * Метод фиксации факта возвращения книги.
     *
     * @param returnDate дата возвращения книги
     */
    public void returnBook(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Переопределенный метод equals().
     * Объекты считаются равными, если совпадает ISBN, читатель и дата выдачи.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowing borrowing = (Borrowing) o;
        return Objects.equals(isbn, borrowing.isbn) &&
                Objects.equals(readerId, borrowing.readerId) &&
                Objects.equals(borrowDate, borrowing.borrowDate);
    }

    /**
     * Переопределенный метод hashCode().
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, readerId, borrowDate);
    }

    /**
     * Переопределенный метод toString() для удобного вывода информации о классе.
     */
    @Override
    public String toString() {
        return "Borrowing{" +
                "isbn='" + isbn + '\'' +
                ", readerId='" + readerId + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}