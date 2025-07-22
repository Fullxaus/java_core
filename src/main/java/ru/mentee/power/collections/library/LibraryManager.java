package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryManager {
    // Коллекция для хранения книг (ISBN -> Book)
    private final Map<String, Book> booksByIsbn;

    // Коллекция для хранения читателей (ID -> Reader)
    private final Map<String, Reader> readersById;

    // Коллекция для хранения истории выдач
    private final List<Borrowing> borrowings;

    // Коллекция для группировки книг по жанрам (Genre -> Set<Book>)
    private final Map<Book.Genre, Set<Book>> booksByGenre;

    // Коллекция для хранения авторов и их книг (автор -> List<Book>)
    private final Map<String, List<Book>> booksByAuthor;

    // Конструктор
    public LibraryManager() {
        this.booksByIsbn = new HashMap<>();
        this.readersById = new HashMap<>();
        this.borrowings = new ArrayList<>();
        this.booksByGenre = new HashMap<>();
        this.booksByAuthor = new HashMap<>();

        // Инициализация жанров
        for (Book.Genre genre : Book.Genre.values()) {
            booksByGenre.put(genre, new HashSet<>());
        }
    }

    // =========== Методы для работы с книгами ===========

    /**
     * Добавляет книгу в библиотеку.
     * @param book Новая книга
     * @return true, если книга успешно добавлена
     */
    public boolean addBook(Book book) {
        if (!booksByIsbn.containsKey(book.getIsbn())) {
            booksByIsbn.put(book.getIsbn(), book);

            // Добавляем книгу в соответствующий жанр
            booksByGenre.get(book.getGenre()).add(book);

            // Добавляем книгу в индекс авторов
            for (String author : book.getAuthors()) {
                booksByAuthor.computeIfAbsent(author, a -> new ArrayList<>()).add(book);
            }

            return true;
        }
        return false;
    }

    /**
     * Получает книгу по её ISBN.
     * @param isbn ISBN книги
     * @return Книга или null, если книга не найдена
     */
    public Book getBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    /**
     * Удаляет книгу из библиотеки.
     * @param isbn ISBN книги
     * @return true, если книга успешно удалена
     */
    public boolean removeBook(String isbn) {
        Book bookToRemove = booksByIsbn.remove(isbn);
        if (bookToRemove != null) {
            // Убираем книгу из соответствующего жанра
            booksByGenre.get(bookToRemove.getGenre()).remove(bookToRemove);

            // Убираем книгу из индексов авторов
            for (String author : bookToRemove.getAuthors()) {
                List<Book> booksOfAuthor = booksByAuthor.get(author);
                if (booksOfAuthor != null) {
                    booksOfAuthor.remove(bookToRemove);

                    // Если у автора больше нет книг, удаляем самого автора
                    if (booksOfAuthor.isEmpty()) {
                        booksByAuthor.remove(author);
                    }
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Возвращает список всех книг в библиотеке.
     * @return Список всех книг
     */
    public Collection<Book> getAllBooks() {
        return booksByIsbn.values();
    }

    /**
     * Возвращает список книг выбранного жанра.
     * @param genre Желаемый жанр
     * @return Список книг указанного жанра
     */
    public List<Book> getBooksByGenre(Book.Genre genre) {
        return new ArrayList<>(booksByGenre.get(genre));
    }

    /**
     * Возвращает список книг указанного автора.
     * @param author Название автора
     * @return Список книг данного автора
     */
    public List<Book> getBooksByAuthor(String author) {
        return booksByAuthor.getOrDefault(author, Collections.emptyList());
    }

    /**
     * Производит поиск книг по фрагменту названия.
     * @param titlePart Частичная строка названия
     * @return Список книг, содержащих указанное название
     */
    public List<Book> searchBooksByTitle(String titlePart) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().contains(titlePart))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список доступных книг.
     * @return Список доступных книг
     */
    public List<Book> getAvailableBooks() {
        return booksByIsbn.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Сортирует список книг по названию.
     * @param books Список книг для сортировки
     * @return Отсортированный список книг
     */
    public List<Book> sortBooksByTitle(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * Сортирует список книг по году публикации (от новых к старым).
     * @param books Список книг для сортировки
     * @return Отсортированный список книг
     */
    public List<Book> sortBooksByPublicationYear(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparingInt(Book::getPublicationYear).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Сортирует список книг по доступности (сперва доступные).
     * @param books Список книг для сортировки
     * @return Отсортированный список книг
     */
    public List<Book> sortBooksByAvailability(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::isAvailable).reversed())
                .collect(Collectors.toList());
    }

    // =========== Методы для работы с читателями ===========

    /**
     * Добавляет нового читателя в библиотеку.
     * @param reader Новый читатель
     * @return true, если читатель успешно добавлен
     */
    public boolean addReader(Reader reader) {
        if (!readersById.containsKey(reader.getId())) {
            readersById.put(reader.getId(), reader);
            return true;
        }
        return false;
    }

    /**
     * Получает информацию о читателе по его ID.
     * @param readerId ID читателя
     * @return Читатель или null, если читатель не найден
     */
    public Reader getReaderById(String readerId) {
        return readersById.get(readerId);
    }

    /**
     * Удаляет читателя из библиотеки.
     * @param readerId ID читателя
     * @return true, если читатель успешно удалён
     */
    public boolean removeReader(String readerId) {
        return readersById.remove(readerId) != null;
    }

    /**
     * Возвращает список всех зарегистрированных читателей.
     * @return Список читателей
     */
    public Collection<Reader> getAllReaders() {
        return readersById.values();
    }

    // =========== Методы для выдачи и возврата книг ===========

    /**
     * Выдает книгу указанному читателю.
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @param daysСрок, на который выдается книга
     * @return true, если книга успешно выдана
     */
    public boolean borrowBook(String isbn, String readerId, int days) {
        Book book = booksByIsbn.get(isbn);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            Borrowing borrowing = new Borrowing(isbn, readerId, LocalDate.now(), LocalDate.now().plusDays(days));
            borrowings.add(borrowing);
            return true;
        }
        return false;
    }

    /**
     * Осуществляет возврат книги.
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @return true, если книга успешно возвращена
     */
    public boolean returnBook(String isbn, String readerId) {
        Optional<Borrowing> borrowingOpt = borrowings.stream()
                .filter(b -> b.getIsbn().equals(isbn) && b.getReaderId().equals(readerId))
                .findFirst();
        if (borrowingOpt.isPresent()) {
            Borrowing borrowing = borrowingOpt.get();
            borrowing.setReturnDate(LocalDate.now()); // Устанавливаем дату возврата
            booksByIsbn.get(isbn).setAvailable(true); // Доступность книги восстанавливаем
            return true;
        }
        return false;
    }

    /**
     * Возвращает список всех текущих выдач книг.
     * @return Список выдач
     */
    public List<Borrowing> getAllBorrowings() {
        return borrowings;
    }

    /**
     * Возвращает список всех просроченных выдач.
     * @return Список просроченных выдач
     */
    public List<Borrowing> getOverdueBorrowings() {
        return borrowings.stream()
                .filter(Borrowing::isOverdue)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает историю выдач для конкретного читателя.
     * @param readerId ID читателя
     * @return Список выдач для данного читателя
     */
    public List<Borrowing> getBorrowingsByReader(String readerId) {
        return borrowings.stream()
                .filter(b -> b.getReaderId().equals(readerId))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает историю выдач для конкретной книги.
     * @param isbn ISBN книги
     * @return Список выдач для данной книги
     */
    public List<Borrowing> getBorrowingsByBook(String isbn) {
        return borrowings.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    /**
     * Продляет срок аренды книги.
     * @param isbn ISBN книги
     * @param readerId ID читателя
     * @param extraDays Дополнительные дни аренды
     * @return true, если срок успешно продлён
     */
    public boolean extendBorrowingPeriod(String isbn, String readerId, int extraDays) {
        Optional<Borrowing> borrowingOpt = borrowings.stream()
                .filter(b -> b.getIsbn().equals(isbn) && b.getReaderId().equals(readerId))
                .findFirst();
        if (borrowingOpt.isPresent()) {
            Borrowing borrowing = borrowingOpt.get();
            borrowing.setDueDate(borrowing.getDueDate().plusDays(extraDays)); // Изменяем дату возврата
            return true;
        }
        return false;
    }

    // =========== Методы для сбора статистики ===========

    /**
     * Собирает статистику по жанрам: количество книг по каждому жанру.
     * @return Карта "жанр -> количество книг"
     */
    public Map<Book.Genre, Integer> getGenreStatistics() {
        return booksByGenre.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
    }

    /**
     * Возвращает топ популярных книг (по количеству выдач).
     * @param topN Сколько лучших книг вернуть
     * @return Топ популярных книг
     */
    public List<Map.Entry<Book, Integer>> getTopPopularBooks(int topN) {
        // Сначала считаем количество выдач по ISBN
        Map<String, Long> isbnCounts = borrowings.stream()
                .collect(Collectors.groupingBy(Borrowing::getIsbn, Collectors.counting()));

        // Преобразуем map ISBN->frequency в map Book->frequency
        Map<Book, Integer> bookCounts = isbnCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> getBookByIsbn(entry.getKey()), // Книга по ISBN
                        entry -> entry.getValue().intValue() // Частота
                ));

        // Сортируем и ограничиваем результат
        return bookCounts.entrySet().stream()
                .sorted(Map.Entry.<Book, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает топ активных читателей (по количеству взятий книг).
     * @param topN Сколько лучших читателей вернуть
     * @return Топ активных читателей
     */
    public List<Map.Entry<Reader, Integer>> getTopActiveReaders(int topN) {
        Map<Reader, Integer> readerCounts = borrowings.stream()
                .map(Borrowing::getReaderId)
                .collect(Collectors.groupingBy(this::getReaderById, Collectors.summingInt(i -> 1)));

        return readerCounts.entrySet().stream()
                .sorted(Map.Entry.<Reader, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * Находит читателей, которые имеют просроченные книги.
     * @return Список читателей с просроченными книгами
     */
    public List<Reader> findReadersWithOverdueBooks() {
        Set<String> overdueReaderIds = borrowings.stream()
                .filter(Borrowing::isOverdue)
                .map(Borrowing::getReaderId)
                .collect(Collectors.toSet());

        return overdueReaderIds.stream()
                .map(readersById::get)
                .collect(Collectors.toList());
    }

    // ======= Вспомогательные приватные методы =======

    private void updateBooksByAuthor(Book book) {
        for (String author : book.getAuthors()) {
            booksByAuthor.computeIfAbsent(author, key -> new ArrayList<>()).add(book);
        }
    }

    private void removeFromBooksByAuthor(Book book) {
        for (String author : book.getAuthors()) {
            List<Book> booksOfAuthor = booksByAuthor.get(author);
            if (booksOfAuthor != null) {
                booksOfAuthor.remove(book);

                // Если у автора больше нет книг, удаляем его из карты
                if (booksOfAuthor.isEmpty()) {
                    booksByAuthor.remove(author);
                }
            }
        }
    }
    public List<Reader> getReadersWithOverdueBooks() {
        List<Reader> overdueReaders = new ArrayList<>();

        // Проходим по всем выдачам книг
        for (Borrowing borrowing : borrowings) {
            // Проверяем, является ли выдача просроченной и не возвращённой
            if (borrowing.isOverdue() && !borrowing.isReturned()) {
                // Добавляем читателя в список, если у него есть хотя бы одна просроченная книга
                overdueReaders.add(getReaderById(borrowing.getReaderId()));
            }
        }

        return overdueReaders;
    }

    public Iterator<Book> getBooksByGenreAndYearIterator(Book.Genre genre, int year) {
        // Создаем список, куда поместим подходящие книги
        List<Book> filteredBooks = new ArrayList<>();

        // Проходим по всем книгам и фильтруем по жанру и году
        for (Book book : booksByIsbn.values()) {
            if (book.getGenre() == genre && book.getPublicationYear() == year) {
                filteredBooks.add(book);
            }
        }

        // Возвращаем итератор по подобранным книгам
        return filteredBooks.iterator();
    }
    public Iterator<Book> getBooksWithMultipleAuthorsIterator(int minAuthorsCount) {
        // Создаем список, куда помещаем книги с требуемым числом авторов
        List<Book> filteredBooks = new ArrayList<>();

        // Перебираем все книги и фильтруем по минимальному количеству авторов
        for (Book book : booksByIsbn.values()) {
            if (book.getAuthors().size() >= minAuthorsCount) {
                filteredBooks.add(book);
            }
        }

        // Возвращаем итератор по подобранным книгам
        return filteredBooks.iterator();
    }

    public Iterator<Borrowing> getOverdueBorrowingsIterator() {
        // Создаем список просроченных выдач
        List<Borrowing> overdueBorrowings = new ArrayList<>();

        // Проверяем все записи о выдаче книг
        for (Borrowing borrowing : borrowings) {
            // Если книга просрочена и не возвращена
            if (borrowing.isOverdue() && !borrowing.isReturned()) {
                overdueBorrowings.add(borrowing);
            }
        }

        // Возвращаем итератор по просроченным выдачам
        return overdueBorrowings.iterator();
    }
}