package ru.mentee.power.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Класс для управления каталогом книг в библиотеке.
 */
public class BookCatalog {

    private final List<Book> books;

    /**
     * Создаёт пустой каталог книг.
     */
    public BookCatalog() {
        this.books = new ArrayList<>();
    }

    /**
     * Добавляет книгу в каталог.
     *
     * @param book книга для добавления
     */
    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    /**
     * Возвращает неизменяемый список всех книг в каталоге.
     *
     * @return список книг
     */
    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    /**
     * Сортирует книги по заданному компаратору.
     *
     * @param comparator компаратор для сортировки
     * @return новый отсортированный список книг (исходный список не меняется)
     */
    public List<Book> sortBooks(Comparator<Book> comparator) {
        return books.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Фильтрует книги по заданному условию.
     *
     * @param predicate условие фильтрации
     * @return новый список книг, удовлетворяющих условию
     */
    public List<Book> filterBooks(Predicate<Book> predicate) {
        return books.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * @return компаратор для сортировки по названию (по алфавиту)
     */
    public static Comparator<Book> byTitle() {
        return Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * @return компаратор для сортировки по автору (по алфавиту)
     */
    public static Comparator<Book> byAuthor() {
        return Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * @return компаратор для сортировки по году издания (от старых к новым)
     */
    public static Comparator<Book> byYearPublished() {
        return Comparator.comparingInt(Book::getYearPublished);
    }

    /**
     * @return компаратор для сортировки по количеству страниц
     *         (от меньшего к большему)
     */
    public static Comparator<Book> byPageCount() {
        return Comparator.comparingInt(Book::getPageCount);
    }

    /**
     * Создаёт сложный компаратор для сортировки по нескольким критериям.
     *
     * @param comparators список компараторов в порядке приоритета
     * @return композитный компаратор
     */
    public static Comparator<Book> multipleComparators(
            List<Comparator<Book>> comparators) {
        if (comparators == null || comparators.isEmpty()) {
            return (b1, b2) -> 0; // Если нет критериев — всегда равны.
        }

        Comparator<Book> composite = comparators.get(0);
        for (int i = 1; i < comparators.size(); i++) {
            composite = composite.thenComparing(comparators.get(i));
        }
        return composite;
    }
}