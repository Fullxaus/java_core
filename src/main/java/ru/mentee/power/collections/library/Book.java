package ru.mentee.power.collections.library;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book {
    private String isbn;
    private String title;
    private Set<String> authors;
    private Genre genre;
    private int publicationYear;
    private int pageCount;
    private boolean available;

    public enum Genre {
        FICTION, NON_FICTION, SCIENCE, HISTORY, FANTASY, DETECTIVE, ROMANCE, BIOGRAPHY, CHILDREN,PROGRAMMING,SCIENTIFIC
    }

    public Book(String isbn, String title, int publicationYear, Genre genre) {
        this.isbn = isbn;
        this.title = title;
        this.authors = new HashSet<>();
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.available = true;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public Set<String> getAuthors() { return authors; }
    public Genre getGenre() { return genre; }
    public int getPublicationYear() { return publicationYear; }
    public int getPageCount() { return pageCount; }
    public boolean isAvailable() { return available; }

    public void setPageCount(int pageCount) { this.pageCount = pageCount; }
    public void setAvailable(boolean available) { this.available = available; }

    public void addAuthor(String author) {
        authors.add(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genre=" + genre +
                ", publicationYear=" + publicationYear +
                ", pageCount=" + pageCount +
                ", available=" + available +
                '}';
    }
}
