package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Менеджер библиотеки. Держит в памяти книги, читателей и историю выдач.
 */
public class LibraryManager {

    /** Коллекция для хранения книг (ISBN -> Book). */
    private final Map<String, Book> booksByIsbn;

    /** Коллекция для хранения читателей (ID -> Reader). */
    private final Map<String, Reader> readersById;

    /** Коллекция для хранения истории выдач. */
    private final List<Borrowing> borrowings;

    /** Коллекция для группировки книг по жанрам (Genre -> Set&lt;Book&gt;). */
    private final Map<Book.Genre, Set<Book>> booksByGenre;

    /** Коллекция для хранения авторов и их книг (автор -> List&lt;Book&gt;). */
    private final Map<String, List<Book>> booksByAuthor;

    /**
     * Конструктор. Инициализирует все внутренние структуры и создает пустые множества
     * в booksByGenre для каждого жанра.
     */
    public LibraryManager() {
        this.booksByIsbn = new HashMap<>();
        this.readersById = new HashMap<>();
        this.borrowings = new ArrayList<>();
        this.booksByGenre = new HashMap<>();
        this.booksByAuthor = new HashMap<>();

        for (Book.Genre genre : Book.Genre.values()) {
            booksByGenre.put(genre, new HashSet<>());
        }
    }

    // ===========================================================================
    // Методы для работы с книгами
    // ===========================================================================

    /**
     * Добавляет книгу в библиотеку.
     *
     * @param book новая книга
     * @return {@code true}, если книга успешно добавлена, иначе {@code false}
     */
    public boolean addBook(Book book) {
        if (booksByIsbn.containsKey(book.getIsbn())) {
            return false;
        }

        booksByIsbn.put(book.getIsbn(), book);
        booksByGenre.get(book.getGenre()).add(book);

        for (String author : book.getAuthors()) {
            booksByAuthor
                    .computeIfAbsent(author, key -> new ArrayList<>())
                    .add(book);
        }

        return true;
    }

    /**
     * Возвращает книгу по ISBN.
     *
     * @param isbn ISBN книги
     * @return объект Book или {@code null}, если не найдено
     */
    public Book getBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    /**
     * Удаляет книгу из библиотеки по ISBN.
     *
     * @param isbn ISBN книги
     * @return {@code true}, если книга удалена, иначе {@code false}
     */
    public boolean removeBook(String isbn) {
        Book removed = booksByIsbn.remove(isbn);
        if (removed == null) {
            return false;
        }

        booksByGenre.get(removed.getGenre()).remove(removed);
        for (String author : removed.getAuthors()) {
            List<Book> byAuthor = booksByAuthor.get(author);
            byAuthor.remove(removed);
            if (byAuthor.isEmpty()) {
                booksByAuthor.remove(author);
            }
        }

        return true;
    }

    /**
     * Возвращает все книги в библиотеке.
     *
     * @return коллекция всех книг
     */
    public Collection<Book> getAllBooks() {
        return booksByIsbn.values();
    }

    /**
     * Возвращает все книги заданного жанра.
     *
     * @param genre жанр
     * @return список книг указанного жанра
     */
    public List<Book> getBooksByGenre(Book.Genre genre) {
        return new ArrayList<>(booksByGenre.get(genre));
    }

    /**
     * Возвращает все книги указанного автора.
     *
     * @param author имя автора
     * @return список книг данного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        return booksByAuthor.getOrDefault(author, Collections.emptyList());
    }

    /**
     * Ищет книги по части названия.
     *
     * @param titlePart часть строки названия
     * @return список книг, в названии которых встречается подстрока
     */
    public List<Book> searchBooksByTitle(String titlePart) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().contains(titlePart))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает доступные для выдачи книги.
     *
     * @return список доступных книг
     */
    public List<Book> getAvailableBooks() {
        return booksByIsbn.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Сортирует переданный список книг по названию в алфавитном порядке.
     *
     * @param books список для сортировки
     * @return новый отсортированный список
     */
    public List<Book> sortBooksByTitle(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * Сортирует переданный список книг по году публикации (от новых к старым).
     *
     * @param books список для сортировки
     * @return новый отсортированный список
     */
    public List<Book> sortBooksByPublicationYear(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparingInt(Book::getPublicationYear).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Сортирует переданный список книг по доступности (сначала доступные).
     *
     * @param books список для сортировки
     * @return новый отсортированный список
     */
    public List<Book> sortBooksByAvailability(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::isAvailable).reversed())
                .collect(Collectors.toList());
    }

    // ===========================================================================
    // Методы для работы с читателями
    // ===========================================================================

    /**
     * Регистрирует нового читателя.
     *
     * @param reader объект Reader
     * @return {@code true}, если читатель добавлен, иначе {@code false}
     */
    public boolean addReader(Reader reader) {
        if (readersById.containsKey(reader.getId())) {
            return false;
        }
        readersById.put(reader.getId(), reader);
        return true;
    }

    /**
     * Возвращает читателя по ID.
     *
     * @param readerId ID читателя
     * @return объект Reader или {@code null}, если не найден
     */
    public Reader getReaderById(String readerId) {
        return readersById.get(readerId);
    }

    /**
     * Удаляет читателя по ID.
     *
     * @param readerId ID читателя
     * @return {@code true}, если удалён, иначе {@code false}
     */
    public boolean removeReader(String readerId) {
        return readersById.remove(readerId) != null;
    }

    /**
     * Возвращает всех зарегистрированных читателей.
     *
     * @return коллекция читателей
     */
    public Collection<Reader> getAllReaders() {
        return readersById.values();
    }

    // ===========================================================================
    // Методы для выдачи и возврата книг
    // ===========================================================================

    /**
     * Выдаёт книгу читателю на заданный срок.
     *
     * @param isbn     ISBN книги
     * @param readerId ID читателя
     * @param days     срок в днях
     * @return {@code true}, если выдача успешна, иначе {@code false}
     */
    public boolean borrowBook(String isbn, String readerId, int days) {
        Book book = booksByIsbn.get(isbn);
        if (book == null || !book.isAvailable()) {
            return false;
        }
        book.setAvailable(false);
        Borrowing b = new Borrowing(isbn, readerId, LocalDate.now(), LocalDate.now().plusDays(days));
        borrowings.add(b);
        return true;
    }

    /**
     * Возвращает книгу от читателя.
     *
     * @param isbn     ISBN книги
     * @param readerId ID читателя
     * @return {@code true}, если возврат успешен, иначе {@code false}
     */
    public boolean returnBook(String isbn, String readerId) {
        Optional<Borrowing> opt = borrowings.stream()
                .filter(b -> b.getIsbn().equals(isbn) && b.getReaderId().equals(readerId))
                .findFirst();
        if (!opt.isPresent()) {
            return false;
        }
        Borrowing b = opt.get();
        b.setReturnDate(LocalDate.now());
        booksByIsbn.get(isbn).setAvailable(true);
        return true;
    }

    /**
     * Возвращает историю всех выдач.
     *
     * @return список Borrowing
     */
    public List<Borrowing> getAllBorrowings() {
        return borrowings;
    }

    /**
     * Возвращает все просроченные (и ещё не возвращённые) выдачи.
     *
     * @return список просроченных выдач
     */
    public List<Borrowing> getOverdueBorrowings() {
        return borrowings.stream()
                .filter(Borrowing::isOverdue)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает историю выдач по читателю.
     *
     * @param readerId ID читателя
     * @return список выдач данного читателя
     */
    public List<Borrowing> getBorrowingsByReader(String readerId) {
        return borrowings.stream()
                .filter(b -> b.getReaderId().equals(readerId))
                .collect(Collectors.toList());
    }

    /**getReadersWithOverdueBooks
     * Возвращает историю выдач по книге.
     *
     * @param isbn ISBN книги
     * @return список выдач данной книги
     */
    public List<Borrowing> getBorrowingsByBook(String isbn) {
        return borrowings.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    /**
     * Продлевает срок возврата книги.
     *
     * @param isbn       ISBN книги
     * @param readerId   ID читателя
     * @param extraDays  дополнительное число дней
     * @return {@code true}, если продление выполнено, иначе {@code false}
     */
    public boolean extendBorrowingPeriod(String isbn, String readerId, int extraDays) {
        Optional<Borrowing> opt = borrowings.stream()
                .filter(b -> b.getIsbn().equals(isbn) && b.getReaderId().equals(readerId))
                .findFirst();
        if (!opt.isPresent()) {
            return false;
        }
        Borrowing b = opt.get();
        b.setDueDate(b.getDueDate().plusDays(extraDays));
        return true;
    }

    // ===========================================================================
    // Методы для статистики
    // ===========================================================================

    /**
     * Считает, сколько книг в каждом жанре.
     *
     * @return Map жанр → количество книг
     */
    public Map<Book.Genre, Integer> getGenreStatistics() {
        return booksByGenre.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        e -> e.getValue().size()
                ));
    }

    /**
     * Возвращает топ N популярных книг (по количеству выдач).
     *
     * @param topN сколько вернуть
     * @return список пар (Book, count)
     */
    public List<Entry<Book, Integer>> getTopPopularBooks(int topN) {
        Map<String, Long> isbnCounts = borrowings.stream()
                .collect(Collectors.groupingBy(Borrowing::getIsbn, Collectors.counting()));
        Map<Book, Integer> bookCounts = isbnCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> getBookByIsbn(e.getKey()),
                        e -> e.getValue().intValue()
                ));
        return bookCounts.entrySet().stream()
                .sorted(Entry.<Book, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает топ N активных читателей (по количеству взятых книг).
     *
     * @param topN сколько вернуть
     * @return список пар (Reader, count)
     */
    public List<Entry<Reader, Integer>> getTopActiveReaders(int topN) {
        Map<Reader, Integer> readerCounts = borrowings.stream()
                .collect(Collectors.groupingBy(
                        b -> getReaderById(b.getReaderId()),
                        Collectors.summingInt(b -> 1)
                ));
        return readerCounts.entrySet().stream()
                .sorted(Entry.<Reader, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * Находит читателей с хотя бы одной просроченной выдачей.
     *
     * @return список читателей
     */
    public List<Reader> findReadersWithOverdueBooks() {
        return borrowings.stream()
                .filter(Borrowing::isOverdue)
                .map(b -> getReaderById(b.getReaderId()))
                .distinct()
                .collect(Collectors.toList());
    }

    // ===========================================================================
    // Итераторы по выборкам
    // ===========================================================================

    /**
     * Итератор книг по жанру и году.
     *
     * @param genre жанр
     * @param year  год публикации
     * @return итератор по найденным книгам
     */
    public Iterator<Book> getBooksByGenreAndYearIterator(Book.Genre genre, int year) {
        List<Book> filtered = booksByIsbn.values().stream()
                .filter(b -> b.getGenre() == genre && b.getPublicationYear() == year)
                .collect(Collectors.toList());
        return filtered.iterator();
    }

    /**
     * Итератор книг, у которых не менее {@code minAuthorsCount} авторов.
     *
     * @param minAuthorsCount минимальное число авторов
     * @return итератор по найденным книгам
     */
    public Iterator<Book> getBooksWithMultipleAuthorsIterator(int minAuthorsCount) {
        List<Book> filtered = booksByIsbn.values().stream()
                .filter(b -> b.getAuthors().size() >= minAuthorsCount)
                .collect(Collectors.toList());
        return filtered.iterator();
    }

    /**
     * Итератор по просроченным и ещё не возвращённым выдачам.
     *
     * @return итератор по просроченным выдачам
     */
    public Iterator<Borrowing> getOverdueBorrowingsIterator() {
        List<Borrowing> filtered = borrowings.stream()
                .filter(b -> b.isOverdue() && !b.isReturned())
                .collect(Collectors.toList());
        return filtered.iterator();
    }
}