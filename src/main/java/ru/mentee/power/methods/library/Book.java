package ru.mentee.power.methods.library;

public class Book {
    private String title;
    private String author;
    private int year;
    private boolean available;

    /**
     * Создает новую книгу
     *
     * @param title     название книги
     * @param author    автор книги
     * @param year      год издания
     * @param available статус доступности книги при создании
     */
    public Book(String title, String author, int year, boolean available) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = available;
    }

    /**
     * Создает новую книгу (по умолчанию доступна)
     *
     * @param title  название книги
     * @param author автор книги
     * @param year   год издания
     */
    public Book(String title, String author, int year) {
        this(title, author, year, true);
    }

    /**
     * @return название книги
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return автор книги
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return год издания книги
     */
    public int getYear() {
        return year;
    }

    /**
     * @return доступность книги: true — доступна, false — выдана
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Устанавливает статус доступности книги
     *
     * @param available true, если книга доступна; false, если выдана
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return строковое представление информации о книге
     */
    @Override
    public String toString() {
        return String.format(
                "Book{title='%s', author='%s', year=%d, available=%s}",
                title,
                author,
                year,
                available ? "yes" : "no"
        );
    }
}
