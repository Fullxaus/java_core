package ru.mentee.power.comparators;

import java.util.Objects;

/**
 * Класс, представляющий книгу в библиотеке.
 */
public final class Book {

    private final String title;
    private final String author;
    private final int yearPublished;
    private final int pageCount;
    private final String genre;

    /**
     * Создает новый экземпляр книги.
     *
     * @param title         название книги (не {@code null})
     * @param author        автор (не {@code null})
     * @param yearPublished год издания
     * @param pageCount     количество страниц
     * @param genre         жанр (не {@code null})
     * @throws NullPointerException если {@code title}, {@code author} или {@code genre} равны {@code null}
     */
    public Book(
            String title,
            String author,
            int yearPublished,
            int pageCount,
            String genre) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.author = Objects.requireNonNull(author, "author cannot be null");
        this.yearPublished = yearPublished;
        this.pageCount = pageCount;
        this.genre = Objects.requireNonNull(genre, "genre cannot be null");
    }

    /**
     * Возвращает название книги.
     *
     * @return название книги
     */
    public String getTitle() {
        return title;
    }

    /**
     * Возвращает автора книги.
     *
     * @return автор книги
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Возвращает год публикации книги.
     *
     * @return год издания
     */
    public int getYearPublished() {
        return yearPublished;
    }

    /**
     * Возвращает количество страниц в книге.
     *
     * @return количество страниц
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Возвращает жанр книги.
     *
     * @return жанр
     */
    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + author
                + " (" + yearPublished + "), "
                + pageCount + " pages, "
                + genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return yearPublished == book.yearPublished
                && pageCount == book.pageCount
                && title.equals(book.title)
                && author.equals(book.author)
                && genre.equals(book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, yearPublished, pageCount, genre);
    }
}