package ru.mentee.power.collections.library;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Модель одной операции выдачи книги читателю.
 * Содержит информацию об ISBN, читателе, датах выдачи,
 * сроке возврата и фактическом возврате.
 */
public class Borrowing implements Serializable {

    /** ISBN книги. */
    private final String isbn;

    /** Идентификатор читателя. */
    private final String readerId;

    /** Дата выдачи книги. */
    private final LocalDate borrowDate;

    /** Дата, к которой книгу необходимо вернуть. */
    private LocalDate dueDate;

    /** Фактическая дата возврата книги. Null, если не возвращена. */
    private LocalDate returnDate;
    private static final long serialVersionUID = 1L;

    /**
     * Конструктор создаёт запись о выдаче книги.
     *
     * @param isbn       ISBN книги
     * @param readerId   ID читателя
     * @param borrowDate дата выдачи
     * @param dueDate    дата, к которой книгу нужно вернуть
     */
    public Borrowing(String isbn,
                     String readerId,
                     LocalDate borrowDate,
                     LocalDate dueDate) {
        this.isbn = isbn;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    /** @return ISBN книги */
    public String getIsbn() {
        return isbn;
    }

    /** @return ID читателя */
    public String getReaderId() {
        return readerId;
    }

    /** @return дата выдачи книги */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /** @return дата, к которой книгу нужно вернуть */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /** @return дата фактического возврата или {@code null}, если ещё не возвращена */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Устанавливает новую дату срока возврата.
     *
     * @param dueDate новая дата возврата
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Устанавливает фактическую дату возврата книги.
     *
     * @param returnDate дата фактического возврата
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Помечает книгу как возвращённую на указанную дату.
     *
     * @param returnDate дата возврата
     */
    public void returnBook(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Проверяет, просрочена ли выдача.
     * Считается просроченной, если сегодня позже dueDate и книга не возвращена.
     *
     * @return {@code true}, если просрочено и нет returnDate
     */
    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    /**
     * Проверяет, была ли книга возвращена.
     *
     * @return {@code true}, если returnDate не {@code null}
     */
    public boolean isReturned() {
        return returnDate != null;
    }

    /**
     * Два объекта Borrowing равны, если совпадают ISBN, ID читателя и дата выдачи.
     *
     * @param o другой объект
     * @return {@code true}, если равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Borrowing)) {
            return false;
        }
        Borrowing that = (Borrowing) o;
        return Objects.equals(isbn, that.isbn)
                && Objects.equals(readerId, that.readerId)
                && Objects.equals(borrowDate, that.borrowDate);
    }

    /**
     * Хэш рассчитывается на основе ISBN, readerId и borrowDate.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, readerId, borrowDate);
    }

    /**
     * Читаемое строковое представление для отладки и логирования.
     *
     * @return строка с полями Borrowing
     */
    @Override
    public String toString() {
        return "Borrowing{"
                + "isbn='"
                + isbn
                + '\''
                + ", readerId='"
                + readerId
                + '\''
                + ", borrowDate="
                + borrowDate
                + ", dueDate="
                + dueDate
                + ", returnDate="
                + returnDate
                + '}';
    }
}