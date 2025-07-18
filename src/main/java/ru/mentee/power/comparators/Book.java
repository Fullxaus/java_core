package ru.mentee.power.comparators;

/**
 * Класс, представляющий книгу в библиотеке
 */
public class Book {
    private String title;      // Название книги
    private String author;     // Автор
    private int yearPublished; // Год издания
    private int pageCount;     // Количество страниц
    private String genre;      // Жанр

    public Book(String title, String author, int yearPublished, int pageCount, String genre) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.pageCount = pageCount;
        this.genre = genre;
    }

    // Геттеры

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public int getPageCount() {
        return pageCount;
    }
    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + author + " (" + yearPublished +
                "), " + pageCount + " pages, " + genre;
    }
}
