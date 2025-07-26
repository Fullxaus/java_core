package ru.mentee.power.collections.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.*;

public class LibraryManagerTest {

    private LibraryManager libraryManager;
    private Book book1, book2, book3;
    private Reader reader1, reader2;

    @BeforeEach
    void setUp() {
        // Инициализируем библиотекаря
        libraryManager = new LibraryManager();

        // Создаем три разные книги
        book1 = new Book("978-3-16-148410-0", "Java: The Complete Reference", 2023, Book.Genre.SCIENCE);
        book1.addAuthor("Herbert Schildt");

        book2 = new Book("978-1-491-91205-8", "Clean Code", 2008, Book.Genre.SCIENCE);
        book2.addAuthor("Robert C. Martin");

        book3 = new Book("978-0-321-35668-0", "Refactoring: Improving the Design of Existing Code", 1999, Book.Genre.SCIENCE);
        book3.addAuthor("Martin Fowler");

        // Добавляем книги в библиотеку
        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);

        // Создаем двух разных читателей
        reader1 = new Reader("RDR001", "Иван Петров", "ivan@example.com", Reader.ReaderCategory.STUDENT);
        reader2 = new Reader("RDR002", "Анна Смирнова", "anna@example.com", Reader.ReaderCategory.TEACHER);

        // Добавляем читателей в библиотеку
        libraryManager.addReader(reader1);
        libraryManager.addReader(reader2);
    }

    // === 1. Тесты CRUD операций с книгами ===

    @Nested
    @DisplayName("Тесты CRUD операций с книгами")
    class BookCrudTests {

        @Test
        @DisplayName("Должен корректно добавлять книгу в библиотеку")
        void shouldAddBookCorrectly() {
            // Дано: новая книга, которой нет в библиотеке
            Book newBook = new Book("978-1-59059-758-6", "Head First Java", 2018, Book.Genre.SCIENCE);
            newBook.addAuthor("Kathy Sierra");

            // Когда: добавляем книгу
            boolean result = libraryManager.addBook(newBook);

            // Тогда: книга добавляется успешно
            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn("978-1-59059-758-6")).isEqualTo(newBook);
        }

        @Test
        @DisplayName("Не должен добавлять дубликат книги")
        void shouldNotAddDuplicateBook() {
            // Дано: книга с ISBN, который уже существует
            Book existingBook = new Book("978-3-16-148410-0", "Java: The Complete Reference", 2023, Book.Genre.SCIENCE);
            existingBook.addAuthor("Herbert Schildt");

            // Когда: пытаемся добавить дубликат
            boolean result = libraryManager.addBook(existingBook);

            // Тогда: книга не добавляется
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Должен возвращать книгу по ISBN")
        void shouldReturnBookByIsbn() {
            // Дано: известный ISBN
            String knownIsbn = "978-3-16-148410-0";

            // Когда: запрашиваем книгу по ISBN
            Book retrievedBook = libraryManager.getBookByIsbn(knownIsbn);

            // Тогда: книга находится
            assertThat(retrievedBook).isEqualTo(book1);
        }

        @Test
        @DisplayName("Должен возвращать null при поиске книги по несуществующему ISBN")
        void shouldReturnNullForNonExistingIsbn() {
            // Дано: несуществующий ISBN
            String unknownIsbn = "978-0-00-000000-0";

            // Когда: запрашиваем книгу по неверному ISBN
            Book retrievedBook = libraryManager.getBookByIsbn(unknownIsbn);

            // Тогда: книга не найдена
            assertThat(retrievedBook).isNull();
        }

        @Test
        @DisplayName("Должен корректно удалять книгу из библиотеки")
        void shouldRemoveBookCorrectly() {
            // Дано: книга с известным ISBN
            String isbnToRemove = "978-1-491-91205-8";

            // Когда: удаляем книгу
            boolean result = libraryManager.removeBook(isbnToRemove);

            // Тогда: книга удаляется успешно
            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn(isbnToRemove)).isNull();
        }

        @Test
        @DisplayName("Должен возвращать false при попытке удалить несуществующую книгу")
        void shouldReturnFalseWhenRemovingNonExistingBook() {
            // Дано: несуществующий ISBN
            String unknownIsbn = "978-0-00-000000-0";

            // Когда: пытаемся удалить книгу
            boolean result = libraryManager.removeBook(unknownIsbn);

            // Тогда: книга не удаляется
            assertThat(result).isFalse();
        }
    }

    // === 2. Тесты поиска и фильтрации книг ===

    @Nested
    @DisplayName("Тесты поиска и фильтрации книг")
    class BookSearchAndFilterTests {

        @Test
        @DisplayName("Должен возвращать список всех книг")
        void shouldReturnAllBooks() {
            // Когда: запрашиваем список всех книг
            Collection<Book> allBooks =libraryManager.getAllBooks();

            // Тогда: возвращаются все книги
            assertThat(allBooks).hasSize(3);
            assertThat(allBooks).containsExactlyInAnyOrder(book1, book2, book3);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного жанра")
        void shouldReturnBooksByGenre() {
            // Дано: интересующий нас жанр — SCIENCE
            Book.Genre targetGenre = Book.Genre.SCIENCE;

            // Когда: запрашиваем книги этого жанра
            List<Book> booksByGenre = libraryManager.getBooksByGenre(targetGenre);

            // Тогда: возвращается нужный список книг
            assertThat(booksByGenre).hasSize(3);
            assertThat(booksByGenre).containsExactlyInAnyOrder(book1, book2, book3);
        }

        @Test
        @DisplayName("Должен возвращать список книг определенного автора")
        void shouldReturnBooksByAuthor() {
            // Дано: интересующий нас автор — Herbert Schildt
            String targetAuthor = "Herbert Schildt";

            // Когда: запрашиваем книги этого автора
            List<Book> booksByAuthor = libraryManager.getBooksByAuthor(targetAuthor);

            // Тогда: возвращается нужная книга
            assertThat(booksByAuthor).hasSize(1);
            assertThat(booksByAuthor).containsExactly(book1);
        }

        @Test
        @DisplayName("Должен находить книги по части названия")
        void shouldFindBooksByTitlePart() {
            // Дано: часть названия книги — "Java"
            String titlePart = "Java";

            // Когда: выполняем поиск
            List<Book> matchingBooks = libraryManager.searchBooksByTitle(titlePart);

            // Тогда: находятся подходящие книги
            assertThat(matchingBooks).hasSize(1);
            assertThat(matchingBooks).containsExactly(book1);
        }

        @Test
        @DisplayName("Должен возвращать только доступные книги")
        void shouldReturnOnlyAvailableBooks() {
            // Дано: часть книг становится недоступной
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14);

            // Когда: запрашиваем доступные книги
            List<Book> availableBooks = libraryManager.getAvailableBooks();

            // Тогда: возвращается список доступных книг
            assertThat(availableBooks).hasSize(2);
            assertThat(availableBooks).doesNotContain(book1);
        }
    }

    // === 3. Тесты сортировки книг ===

    @Nested
    @DisplayName("Тесты сортировки книг")
    public class BookSortingTests {

        @Test
        @DisplayName("Должен корректно сортировать книги по названию")
        void shouldSortBooksByTitle() {
            // Дано: список всех книг
            List<Book> unsortedBooks = new ArrayList<>(libraryManager.getAllBooks());

            // Когда: сортируем книги по названию
            List<Book> sortedBooks = libraryManager.sortBooksByTitle(unsortedBooks);

            // Тогда: книги отсортированы по алфавиту
            assertThat(sortedBooks).extracting(Book::getTitle).containsSequence(
                    "Clean Code",
                    "Java: The Complete Reference",
                    "Refactoring: Improving the Design of Existing Code"
            );
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по году публикации")
        void shouldSortBooksByPublicationYear() {
            // Дано: список всех книг
            List<Book> unsortedBooks = new ArrayList<>(libraryManager.getAllBooks());

            // Когда: сортируем книги по году публикации
            List<Book> sortedBooks = libraryManager.sortBooksByPublicationYear(unsortedBooks);

            // Тогда: книги отсортированы от новых к старым
            assertThat(sortedBooks).extracting(Book::getPublicationYear).containsSequence(2023, 2008, 1999);
        }

        @Test
        @DisplayName("Должен корректно сортировать книги по доступности")
        void shouldSortBooksByAvailability() {
            // Дано: часть книг становится недоступной
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14);

            // Когда: сортируем книги по доступности
            List<Book> sortedBooks = libraryManager.sortBooksByAvailability(new ArrayList<>(libraryManager.getAllBooks()));

            // Тогда: сначала идут доступные книги
            assertThat(sortedBooks).first().satisfies(book -> assertThat(book.isAvailable()).isTrue());
        }
    }

    // === 4. Тесты CRUD операций с читателями ===

    @Nested
    @DisplayName("Тесты CRUD операций с читателями")
    class ReaderCrudTests {

        @Test
        @DisplayName("Должен корректно добавлять читателя")
        void shouldAddReaderCorrectly() {
            // Дано: новый читатель
            Reader newReader = new Reader("RDR003", "Алексей Сидоров", "alexey@example.com", Reader.ReaderCategory.VIP);

            // Когда: добавляем читателя
            boolean result = libraryManager.addReader(newReader);

            // Тогда: читатель добавляется успешно
            assertThat(result).isTrue();
            assertThat(libraryManager.getReaderById("RDR003")).isEqualTo(newReader);
        }

        @Test
        @DisplayName("Не должен добавлять дубликат читателя")
        void shouldNotAddDuplicateReader() {
            // Дано: читатель с уже существующим ID
            Reader duplicateReader = new Reader("RDR001", "Иван Петров", "ivan@example.com", Reader.ReaderCategory.STUDENT);

            // Когда: пытаемся добавить дубликат
            boolean result = libraryManager.addReader(duplicateReader);

            // Тогда: читатель не добавляется
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Должен возвращать читателя по ID")
        void shouldReturnReaderById() {
            // Дано: известный ID
            String knownId = "RDR001";

            // Когда: запрашиваем читателя по ID
            Reader retrievedReader = libraryManager.getReaderById(knownId);

            // Тогда: возвращается нужный читатель
            assertThat(retrievedReader).isEqualTo(reader1);
        }

        @Test
        @DisplayName("Должен возвращать null при поиске читателя по несуществующему ID")
        void shouldReturnNullForNonExistingReaderId() {
            // Дано: несуществующий ID
            String unknownId = "RDR999";

            // Когда: запрашиваем читателя по неверному ID
            Reader retrievedReader = libraryManager.getReaderById(unknownId);

            // Тогда: читатель не найден
            assertThat(retrievedReader).isNull();
        }

        @Test
        @DisplayName("Должен корректно удалять читателя")
        void shouldRemoveReaderCorrectly() {
            // Дано: читатель с известным ID
            String readerIdToRemove = "RDR001";

            // Когда: удаляем читателя
            boolean result = libraryManager.removeReader(readerIdToRemove);

            // Тогда: читатель удаляется успешно
            assertThat(result).isTrue();
            assertThat(libraryManager.getReaderById(readerIdToRemove)).isNull();
        }
    }

    // === 5. Тесты операций с выдачей книг ===

    @Nested
    @DisplayName("Тесты операций с выдачей книг")
    class BorrowingOperationsTests {

        @Test
        @DisplayName("Должен корректно оформлять выдачу книги")
        void shouldBorrowBookCorrectly() {
            // Дано: доступная книга и читатель
            String isbn = "978-3-16-148410-0"; // Java: The Complete Reference
            String readerId = "RDR001"; // Иван Петров

            // Когда: выдаем книгу
            boolean result = libraryManager.borrowBook(isbn, readerId, 14);

            // Тогда: книга выдается успешно
            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn(isbn).isAvailable()).isFalse();
        }

        @Test
        @DisplayName("Не должен выдавать недоступную книгу")
        void shouldNotBorrowUnavailableBook() {
            // Дано: недоступная книга (уже выданная)
            String isbn = "978-3-16-148410-0"; // Java: The Complete Reference
            String anotherReaderId = "RDR002"; // Анна Смирнова

            // Сначала выдадим книгу первому читателю
            libraryManager.borrowBook(isbn, "RDR001", 14);

            // Когда: пытаемся выдать ту же книгу другому читателю
            boolean result = libraryManager.borrowBook(isbn, anotherReaderId, 14);

            // Тогда: книга не выдается
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("Должен корректно оформлять возврат книги")
        void shouldReturnBookCorrectly() {
            // Дано: выданная книга
            String isbn = "978-3-16-148410-0"; // Java: The Complete Reference
            String readerId = "RDR001"; // Иван Петров

            // Сначала выдаем книгу
            libraryManager.borrowBook(isbn, readerId, 14);

            // Когда: возвращаем книгу
            boolean result = libraryManager.returnBook(isbn, readerId);

            // Тогда: книга возвращается успешно
            assertThat(result).isTrue();
            assertThat(libraryManager.getBookByIsbn(isbn).isAvailable()).isTrue();
        }

        @Test
        @DisplayName("Должен возвращать список просроченных выдач")
        void shouldReturnOverdueBorrowings() {
            // Дано: одна книга просрочена, другая — нет
            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            // Первая книга не просрочена (на месяц вперед)
            Borrowing validBorrowing =
                    new Borrowing("978-3-16-148410-0", "RDR001", today, today.plusMonths(1));
            libraryManager.getAllBorrowings().add(validBorrowing);

            // Вторая книга просрочена (срок истек вчера)
            Borrowing overdueBorrowing =
                    new Borrowing("978-1-491-91205-8", "RDR002", yesterday, yesterday);
            libraryManager.getAllBorrowings().add(overdueBorrowing);

            // Когда
            List<Borrowing> overdues = libraryManager.getOverdueBorrowings();

            // Тогда
            assertThat(overdues).hasSize(1);
            assertThat(overdues.get(0).getIsbn()).isEqualTo("978-1-491-91205-8");
        }

        @Test
        @DisplayName("Должен корректно продлевать срок выдачи")
        void shouldExtendBorrowingPeriodCorrectly() {
            // Дано: выданная книга
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14);

            // Когда: продлеваем срок ещё на 7 дней
            boolean extended = libraryManager.extendBorrowingPeriod("978-3-16-148410-0", "RDR001", 7);

            // Тогда: успешное продление
            assertThat(extended).isTrue();

            // Извлекаем новую дату возврата
            Borrowing updatedBorrowing = libraryManager.getBorrowingsByBook("978-3-16-148410-0").get(0);
            assertThat(updatedBorrowing.getDueDate()).isEqualTo(LocalDate.now().plusWeeks(3));
        }

    }

    // === 6. Тесты дополнительных функций ===

    @Nested
    @DisplayName("Тесты дополнительных функций")
    class AdditionalFunctionalityTests {

        @Test
        @DisplayName("Должен возвращать статистику по популярным книгам")
        void shouldProvidePopularBooksStats() {
            // Дано: книга многократно выдавалась
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14);
            libraryManager.borrowBook("978-3-16-148410-0", "RDR002", 14);

            // Когда: запрашиваем статистику
            List<Map.Entry<Book, Integer>> popularBooks = libraryManager.getTopPopularBooks(3);

            // Тогда: статистика правильная
            assertThat(popularBooks).hasSize(1);
            assertThat(popularBooks.get(0).getKey()).isEqualTo(book1);
            assertThat(popularBooks.get(0).getValue()).isEqualTo(1);
        }

        @Test
        @DisplayName("Должен возвращать статистику по активностям читателей")
        void shouldTrackReaderActivity() {
            // Дано: читателям выдавали книги
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14);
            libraryManager.borrowBook("978-1-491-91205-8", "RDR002", 14);

            // Когда: запрашиваем статистику
            List<Map.Entry<Reader, Integer>> activeReaders = libraryManager.getTopActiveReaders(3);

            // Тогда: статистика правильная
            assertThat(activeReaders).hasSize(2);
            assertThat(activeReaders).anyMatch(e -> e.getKey().equals(reader1) && e.getValue().equals(1));
            assertThat(activeReaders).anyMatch(e -> e.getKey().equals(reader2) && e.getValue().equals(1));
        }
    }
    @Nested
    @DisplayName("Тесты статистики и отчетов")
    class StatisticsAndReportsTests {

        @Test
        @DisplayName("Должен возвращать корректную статистику по жанрам")
        void shouldReturnCorrectGenreStatistics() {
            // Когда: запрашиваем статистику по жанрам
            Map<Book.Genre, Integer> stats = libraryManager.getGenreStatistics();

            // Тогда: статистика корректна
            assertThat(stats).containsEntry(Book.Genre.SCIENCE, 3);
        }

        @Test
        @DisplayName("Должен возвращать список самых популярных книг")
        void shouldReturnMostPopularBooks() {
            // Дано: несколько выдач для разных книг
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14); // book1 x1
            libraryManager.borrowBook("978-1-491-91205-8", "RDR002", 14); // book2 x1
            libraryManager.borrowBook("978-3-16-148410-0", "RDR002", 14); // book1 x2

            // Когда: запрашиваем список популярных книг
            List<Map.Entry<Book, Integer>> popularBooks = libraryManager.getTopPopularBooks(3);

            // Тогда: книги отсортированы по популярности
            assertThat(popularBooks).hasSize(2);
            assertThat(popularBooks.get(0).getKey()).isEqualTo(book1); // книга book1 должна идти первой
            assertThat(popularBooks.get(0).getValue()).isEqualTo(1); // количество выдач равно 2
            assertThat(popularBooks.get(1).getKey()).isEqualTo(book2); // книга book2 идет следующей
            assertThat(popularBooks.get(1).getValue()).isEqualTo(1); // количество выдач равно 1
        }

        @Test
        @DisplayName("Должен возвращать список самых активных читателей")
        void shouldReturnMostActiveReaders() {
            // Дано: несколько выдач для разных читателей
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14); // reader1 x1
            libraryManager.borrowBook("978-1-491-91205-8", "RDR002", 14); // reader2 x1
            libraryManager.borrowBook("978-3-16-148410-0", "RDR002", 14); // reader2 x2

            // Когда: запрашиваем список активных читателей
            List<Map.Entry<Reader, Integer>> activeReaders = libraryManager.getTopActiveReaders(3);

            // Тогда: читатели отсортированы по активности
            assertThat(activeReaders).hasSize(2);
            assertThat(activeReaders.get(0).getKey()).isEqualTo(reader2);
            assertThat(activeReaders.get(0).getValue()).isEqualTo(1);
        }

        @Test
        @DisplayName("Должен возвращать список читателей с просроченными книгами")
        void shouldReturnReadersWithOverdueBooks() {
            // Дано: выданные книги, одна из которых просрочена
            // book1 (еще не просрочена)
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14); // Выдаем книгу на две недели вперед

            // book2 (просрочена)
            LocalDate now = LocalDate.now();
            LocalDate yesterday = now.minusDays(1); // Yesterday
            Borrowing overdueBorrowing = new Borrowing("978-1-491-91205-8", "RDR002", yesterday, yesterday.plusDays(1));

            // Непосредственное вмешательство в internal state недопустимо!
            // Вместо этого явно вызовем публичный метод для создания просроченной выдачи
            libraryManager.borrowBook("978-1-491-91205-8", "RDR002", -1); // Выдаем книгу на 1 день назад

            // Когда: запрашиваем список читателей с просроченными книгами
            List<Reader> overdueReaders = libraryManager.findReadersWithOverdueBooks();

            // Тогда: Возвращается только тот читатель, у кого книга просрочена
            assertThat(overdueReaders).hasSize(1);
            assertThat(overdueReaders.get(0)).isEqualTo(reader2);
        }
    }

    // ==== 3. Тесты итераторов ====

    @Nested
    @DisplayName("Тесты итераторов")
    class IteratorsTests {

        @Test
        @DisplayName("Должен корректно итерироваться по книгам определенного жанра и года")
        void shouldIterateOverBooksByGenreAndYear() {
            // Дано: добавляем дополнительную книгу другого жанра
            Book book4 = new Book("978-0-321-35668-1", "Effective Java", 2018, Book.Genre.CHILDREN);
            book4.addAuthor("Joshua Bloch");
            libraryManager.addBook(book4);

            // Когда: запрашиваем итератор по научному жанру и году 2023
            Iterator<Book> iterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.SCIENCE, 2023);

            // Тогда: Возвращается только книга с указанным годом и жанром
            List<Book> results = new ArrayList<>();
            while (iterator.hasNext()) {
                results.add(iterator.next());
            }
            assertThat(results).containsExactly(book1);
        }

        @Test
        @DisplayName("Должен корректно итерироваться по книгам с несколькими авторами")
        void shouldIterateOverBooksWithMultipleAuthors() {
            // Дано: добавляем книгу с несколькими авторами
            Book book4 = new Book("978-0-321-35668-1", "Design Patterns", 1995, Book.Genre.SCIENCE);
            book4.addAuthor("Erich Gamma");
            book4.addAuthor("Richard Helm");
            libraryManager.addBook(book4);

            // Когда: запрашиваем итератор по книгам с минимум двумя авторами
            Iterator<Book> iterator = libraryManager.getBooksWithMultipleAuthorsIterator(2);

            // Тогда: Возвращается только книга с несколькими авторами
            List<Book> results = new ArrayList<>();
            while (iterator.hasNext()) {
                results.add(iterator.next());
            }
            assertThat(results).containsExactly(book4);
        }

        @Test
        @DisplayName("Должен корректно итерироваться по просроченным выдачам")
        void shouldIterateOverOverdueBorrowings() {
            // Дано: выдача с просроченной книгой
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14); // book1 (еще не просрочена)
            libraryManager.borrowBook("978-1-491-91205-8", "RDR002", -1); // book2 (просрочена)

            // Когда: запрашиваем итератор по просроченным выдачам
            Iterator<Borrowing> iterator = libraryManager.getOverdueBorrowingsIterator();

            // Тогда: Возвращается только просроченная выдача
            List<Borrowing> results = new ArrayList<>();
            while (iterator.hasNext()) {
                results.add(iterator.next());
            }
            assertThat(results).hasSize(1);
            assertThat(results.get(0).getIsbn()).isEqualTo("978-1-491-91205-8");
        }

        @Test
        @DisplayName("Должен выбрасывать NoSuchElementException при отсутствии следующего элемента")
        void shouldThrowNoSuchElementExceptionWhenNoMoreElements() {
            // Дано: итератор без элементов
            Iterator<Book> emptyIterator = libraryManager.getBooksByGenreAndYearIterator(Book.Genre.HISTORY, 2023);

            // Когда: пытаемся вызвать next()
            assertThatThrownBy(emptyIterator::next).isInstanceOf(NoSuchElementException.class);
        }
    }

}

