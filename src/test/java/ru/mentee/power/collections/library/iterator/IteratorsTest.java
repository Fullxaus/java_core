package ru.mentee.power.collections.library.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.collections.library.Book;
import ru.mentee.power.collections.library.Borrowing;
import ru.mentee.power.collections.library.LibraryManager;
import ru.mentee.power.collections.library.Reader;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class IteratorsTest {

    private LibraryManager libraryManager;
    private Book bookA, bookB, bookC;
    private Reader reader1, reader2;

    @BeforeEach
    void setUp() {
        libraryManager = new LibraryManager();

        // 1) Books
        // bookA: FICTION, 2000, 1 author
        bookA = new Book("ISBN-A", "Alpha", 2000, Book.Genre.FICTION);
        bookA.addAuthor("Author1");

        // bookB: FICTION, 2000, 2 authors
        bookB = new Book("ISBN-B", "Bravo", 2000, Book.Genre.FICTION);
        bookB.addAuthor("Author1");
        bookB.addAuthor("Author2");

        // bookC: SCIENCE, 2010, 3 authors
        bookC = new Book("ISBN-C", "Charlie", 2010, Book.Genre.SCIENCE);
        bookC.addAuthor("Author3");
        bookC.addAuthor("Author4");
        bookC.addAuthor("Author5");

        libraryManager.addBook(bookA);
        libraryManager.addBook(bookB);
        libraryManager.addBook(bookC);

        // 2) Readers
        reader1 = new Reader("R1", "Reader One", "one@example.com", Reader.ReaderCategory.STUDENT);
        reader2 = new Reader("R2", "Reader Two", "two@example.com", Reader.ReaderCategory.TEACHER);
        libraryManager.addReader(reader1);
        libraryManager.addReader(reader2);

        // 3) Borrowings
        LocalDate today = LocalDate.now();
        // a) valid borrowing (not overdue)
        libraryManager.borrowBook(bookA.getIsbn(), reader1.getId(), 30);
        // b) returned borrowing
        libraryManager.borrowBook(bookB.getIsbn(), reader1.getId(), -1); // due yesterday
        // simulate return
        libraryManager.returnBook(bookB.getIsbn(), reader1.getId());
        // c) overdue borrowing (not returned)
        libraryManager.borrowBook(bookC.getIsbn(), reader2.getId(), -1); // due yesterday, still not returned
    }

    @Test
    @DisplayName("Итератор по жанру и году должен возвращать только книги указанного жанра и года")
    void genreAndYearIteratorShouldReturnOnlyMatchingBooks() {
        Iterator<Book> it = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FICTION, 2000);

        List<Book> result = new ArrayList<>();
        while (it.hasNext()) {
            result.add(it.next());
        }

        // We expect bookA and bookB
        assertThat(result)
                .containsExactlyInAnyOrder(bookA, bookB);

        // And none of them has wrong genre/year
        result.forEach(b -> {
            assertThat(b.getGenre()).isEqualTo(Book.Genre.FICTION);
            assertThat(b.getPublicationYear()).isEqualTo(2000);
        });
    }

    @Test
    @DisplayName("Итератор по жанру и году должен вернуть пустой результат, если нет подходящих книг")
    void genreAndYearIteratorShouldReturnEmptyIteratorWhenNoMatches() {
        // no books in ROMANCE / year 1999
        Iterator<Book> it = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.ROMANCE, 1999);
        assertThat(it.hasNext()).isFalse();
        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Итератор для книг с несколькими авторами должен возвращать только книги с указанным минимальным количеством авторов")
    void multipleAuthorsIteratorShouldReturnOnlyBooksWithMultipleAuthors() {
        // minAuthors = 2 -> bookB (2 authors) and bookC (3 authors)
        Iterator<Book> it = libraryManager.getBooksWithMultipleAuthorsIterator(2);

        List<Book> result = new ArrayList<>();
        while (it.hasNext()) {
            result.add(it.next());
        }

        assertThat(result)
                .containsExactlyInAnyOrder(bookB, bookC);

        result.forEach(b ->
                assertThat(b.getAuthors().size()).isGreaterThanOrEqualTo(2));
    }

    @Test
    @DisplayName("Итератор для книг с несколькими авторами должен вернуть пустой результат, если нет подходящих книг")
    void multipleAuthorsIteratorShouldReturnEmptyIteratorWhenNoMatches() {
        // minAuthors = 5 -> no book has so many
        Iterator<Book> it = libraryManager.getBooksWithMultipleAuthorsIterator(5);
        assertThat(it.hasNext()).isFalse();
        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Итератор для просроченных выдач должен возвращать только просроченные и не возвращенные выдачи")
    void overdueBorrowingsIteratorShouldReturnOnlyOverdueBorrowings() {
        Iterator<Borrowing> it = libraryManager.getOverdueBorrowingsIterator();

        List<Borrowing> result = new ArrayList<>();
        while (it.hasNext()) {
            result.add(it.next());
        }

        // We expect exactly one overdue, which is for bookC & reader2
        assertThat(result).hasSize(1);
        Borrowing overdue = result.get(0);
        assertThat(overdue.getIsbn()).isEqualTo(bookC.getIsbn());
        assertThat(overdue.getReaderId()).isEqualTo(reader2.getId());
        assertThat(overdue.isOverdue()).isTrue();
        assertThat(overdue.isReturned()).isFalse();
    }

    @Test
    @DisplayName("Итератор для просроченных выдач должен вернуть пустой результат, если нет просроченных выдач")
    void overdueBorrowingsIteratorShouldReturnEmptyIteratorWhenNoOverdues() {
        // Create a fresh manager without any overdue
        LibraryManager lm2 = new LibraryManager();
        // add one valid borrowing
        lm2.addBook(bookA);
        lm2.addReader(reader1);
        lm2.borrowBook(bookA.getIsbn(), reader1.getId(), 10);

        Iterator<Borrowing> it = lm2.getOverdueBorrowingsIterator();
        assertThat(it.hasNext()).isFalse();
        assertThatThrownBy(it::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Все итераторы должны выбрасывать NoSuchElementException при вызове next() на пустом итераторе")
    void allIteratorsShouldThrowNoSuchElementExceptionWhenEmpty() {
        List<Iterator<?>> its = List.of(
                libraryManager.getBooksByGenreAndYearIterator(Book.Genre.CHILDREN, 1950),
                libraryManager.getBooksWithMultipleAuthorsIterator(10),
                libraryManager.getOverdueBorrowingsIterator()
        );

        for (Iterator<?> it : its) {
            // 1) Досмотрим всё, что выдаёт итератор
            while (it.hasNext()) {
                it.next();
            }

            // 2) Теперь он обязано быть пуст
            assertThat(it.hasNext()).isFalse();

            // 3) При вызове next() — NoSuchElementException
            assertThatThrownBy(it::next)
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("Метод remove() итератора getBooksByGenreAndYearIterator должен удалять элемент")
    void removeOnGenreAndYearIteratorShouldRemoveElement() {
        // 1) Снимаем "снимок" всех элементов genre=FICTION & year=2000
        List<Book> snapshot = new ArrayList<>();
        libraryManager
                .getBooksByGenreAndYearIterator(Book.Genre.FICTION, 2000)
                .forEachRemaining(snapshot::add);

        // Убеждаемся, что не пусто
        assertThat(snapshot)
                .as("Должна быть хотя бы одна книга жанра FICTION 2000-го года")
                .isNotEmpty();

        // 2) Берём новый итератор, удаляем первый элемент
        Iterator<Book> it = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.FICTION, 2000);
        Book removed = it.next();
        it.remove();  // этот remove() должен отработать без исключения

        // 3) Собираем оставшиеся элементы из того же итератора
        List<Book> remaining = new ArrayList<>();
        it.forEachRemaining(remaining::add);

        // 4) Проверяем:
        // - размер уменьшился ровно на 1
        // - удалённый элемент отсутствует
        assertThat(remaining)
                .as("После вызова remove() размер списка должен быть меньше на 1")
                .hasSize(snapshot.size() - 1)
                .doesNotContain(removed);
    }
}
