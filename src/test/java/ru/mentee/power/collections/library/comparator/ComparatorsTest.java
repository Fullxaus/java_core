package ru.mentee.power.collections.library.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.tuple;

public class ComparatorsTest {

    @Test
    @DisplayName("TitleComparator должен сортировать книги по названию в алфавитном порядке")
    void titleComparatorShouldSortBooksAlphabetically() {
        Book b1 = new Book("1", "Zoo Adventure",         2020, Book.Genre.FICTION);
        Book b2 = new Book("2", "alice in Wonderland",  2019, Book.Genre.FICTION);
        Book b3 = new Book("3", "The Hobbit",           2018, Book.Genre.FICTION);
        Book b4 = new Book("4", "alice in Wonderland",  2017, Book.Genre.FICTION);

        List<Book> books = Arrays.asList(b1, b2, b3, b4);
        Collections.sort(books, new TitleComparator());

        assertThat(books)
                .extracting(Book::getTitle)
                .containsExactly(
                        "alice in Wonderland",
                        "alice in Wonderland",  // двойка и четверка меняют порядок по стабильности
                        "The Hobbit",
                        "Zoo Adventure"
                );
    }

    @Test
    @DisplayName("TitleComparator должен корректно обрабатывать null-значения")
    void titleComparatorShouldHandleNullValues() {
        Book b1 = new Book("1", null,                2020, Book.Genre.FICTION);
        Book b2 = new Book("2", "Harry Potter",      2019, Book.Genre.FICTION);
        Book b3 = new Book("3", null,                2018, Book.Genre.FICTION);

        List<Book> books = Arrays.asList(b1, b2, b3);
        Collections.sort(books, new TitleComparator());

        assertThat(books)
                .extracting(Book::getTitle)
                .containsExactly(
                        "Harry Potter",
                        null,
                        null
                );
    }

    @Test
    @DisplayName("PublicationYearComparator должен сортировать книги от новых к старым")
    void publicationYearComparatorShouldSortBooksFromNewToOld() {
        Book b1 = new Book("1", "A", 2023, Book.Genre.SCIENTIFIC);
        Book b2 = new Book("2", "B", 2015, Book.Genre.SCIENTIFIC);
        Book b3 = new Book("3", "C", 2018, Book.Genre.SCIENTIFIC);

        List<Book> books = Arrays.asList(b1, b2, b3);
        Collections.sort(books, new PublicationYearComparator());

        assertThat(books)
                .extracting(Book::getPublicationYear)
                .containsExactly(2023, 2018, 2015);
    }

    @Test
    @DisplayName("AvailabilityComparator должен сортировать сначала доступные, потом недоступные книги")
    void availabilityComparatorShouldSortAvailableFirst() {
        Book b1 = new Book("1", "A", 2020, Book.Genre.PROGRAMMING);
        b1.setAvailable(false);

        Book b2 = new Book("2", "B", 2019, Book.Genre.PROGRAMMING);
        b2.setAvailable(true);

        Book b3 = new Book("3", "C", 2018, Book.Genre.PROGRAMMING);
        b3.setAvailable(false);

        List<Book> books = Arrays.asList(b1, b2, b3);
        Collections.sort(books, new AvailabilityComparator());

        assertThat(books)
                .extracting(Book::isAvailable)
                .containsExactly(true, false, false);
    }

    @Test
    @DisplayName("GenreAndTitleComparator должен сортировать сначала по жанру, потом по названию")
    void genreAndTitleComparatorShouldSortByGenreThenByTitle() {
        Book b1 = new Book("1", "Math Guide",       2020, Book.Genre.SCIENTIFIC);
        Book b2 = new Book("2", "Java in Action",   2019, Book.Genre.PROGRAMMING);
        Book b3 = new Book("3", "Algorithms",       2018, Book.Genre.PROGRAMMING);
        Book b4 = new Book("4", "Zoology",          2017, Book.Genre.SCIENTIFIC);

        List<Book> books = Arrays.asList(b1, b2, b3, b4);
        Collections.sort(books, new GenreAndTitleComparator());

        assertThat(books)
                .extracting(Book::getGenre, Book::getTitle)
                .containsExactly(
                        tuple(Book.Genre.PROGRAMMING, "Algorithms"),
                        tuple(Book.Genre.PROGRAMMING, "Java in Action"),
                        tuple(Book.Genre.SCIENTIFIC,  "Math Guide"),
                        tuple(Book.Genre.SCIENTIFIC,  "Zoology")
                );
    }
}