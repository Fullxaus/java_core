package ru.mentee.power.collections.library;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Менеджер библиотеки. Держит в памяти книги, читателей и историю выдач.
 */
public class LibraryManager implements Serializable {

    private static final long serialVersionUID = 100L;

    private Map<String, Book> booksByIsbn;
    private Map<String, Reader> readersById;
    private List<Borrowing> borrowings;
    private Map<Book.Genre, Set<Book>> booksByGenre;
    private Map<String, List<Book>> booksByAuthor;

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

        // Инициализация наборов для каждого жанра
        for (Book.Genre genre : Book.Genre.values()) {
            booksByGenre.put(genre, new HashSet<>());
        }

        initializeGenreSets();
    }

    public Collection<Book> getALLBooks() {
        return booksByIsbn.values();
    }

    public Collection<Reader> getALLReaders() {
        return readersById.values();
    }
    public Map<Book.Genre, Set<Book>> getBooksByGenre() {
        return booksByGenre; // Предполагается, что booksByGenre инициализирован и содержит данные
    }

    // Метод для получения книги по ISBN
    public Book getBooksByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    // Метод для получения читателя по ID
    public Reader getReadersById(String readerId) {
        return readersById.get(readerId);
    }

    // Метод для получения всех выдач
    public List<Borrowing> getBorrowings() {
        return new ArrayList<>(borrowings);
    }


    public void setBooksByIsbn(Map<String, Book> booksByIsbn) {
        this.booksByIsbn = booksByIsbn;
    }

    public void setReadersById(Map<String, Reader> readersById) {
        this.readersById = readersById;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    private void initializeGenreSets() {
        for (Book.Genre genre : Book.Genre.values()) {
            booksByGenre.put(genre, new HashSet<>());
        }
    }

    public void rebuildIndexes() {
        this.booksByIsbn = new HashMap<>();
        this.booksByGenre = new HashMap<>();
        this.booksByAuthor = new HashMap<>();

        initializeGenreSets();
    }

    // Инициализация данных (при первом запуске)
    public void initializeData() {
        if (booksByIsbn == null) {
            booksByIsbn = new HashMap<>();
        }
        if (readersById == null) {
            readersById = new HashMap<>();
        }
        if (borrowings == null) {
            borrowings = new ArrayList<>();
        }
    }

    // Метод для вывода статистики
    public void printStatistics() {
        System.out.println("Статистика библиотеки:");
        System.out.println("Общее количество книг: " + booksByIsbn.size());


        // Подсчет доступных книг
        long availableBooksCount = booksByIsbn.values().stream().filter(Book::isAvailable).count();
        System.out.println("Количество доступных книг: " + availableBooksCount);

        // Подсчет книг по жанрам
        System.out.println("Количество книг по жанрам:");
        for (Book.Genre genre : Book.Genre.values()) {
            int genreCount = booksByGenre.get(genre).size();
            System.out.printf("%s: %d%n", genre, genreCount);
        }

        // Топ популярных книг
        System.out.println("Топ популярных книг:");
        getTopPopularBooks(3).forEach(entry -> System.out.printf(
                "%s (%d выдач)%n",
                entry.getKey(),
                entry.getValue()));

        // Топ активных читателей
        System.out.println("Топ активных читателей:");
        getTopActiveReaders(3).forEach(entry -> System.out.printf(
                "%s (%d выдач)%n",
                entry.getKey(),
                entry.getValue()));
    }


    public void saveLibraryState(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this); // Записываем всю библиотеку целиком
        }
    }

    public static LibraryManager loadLibraryState(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null; // Нет файла - возвращаем null
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (LibraryManager) ois.readObject(); // Загружаем полную библиотеку
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportBooksToCsv(String filename, String delimiter) {
        String BOOK_CSV_HEADER = "ISBN" + delimiter +
                "Title" + delimiter +
                "Authors" + delimiter +
                "Genre" + delimiter +
                "Publication Year" + delimiter +
                "Page Count" + delimiter +
                "Available";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(BOOK_CSV_HEADER + "\r\n");
            for (Book book : booksByIsbn.values()) { // Итерируемся по всем книгам
                String line = book.getIsbn() + delimiter +
                        book.getTitle() + delimiter +
                        String.join(";", book.getAuthors()) + delimiter + // Объединяем авторов в строку
                        book.getGenre() + delimiter +
                        book.getPublicationYear() + delimiter +
                        book.getPageCount() + delimiter +
                        book.isAvailable() + "\r\n";
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int importBooksFromCsv(String filename, String delimiter, boolean append) {
        int importedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String headerLine = reader.readLine(); // Чтение заголовка
            // Проверка заголовка на соответствие ожидаемому формату
            if (headerLine == null || !headerLine.equals("ISBN" + delimiter + "Title" + delimiter + "Authors" + delimiter + "Genre" + delimiter + "Publication Year" + delimiter + "Page Count" + delimiter + "Available")) {
                throw new IOException("Неверный формат файла");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(delimiter);
                    // Проверка на количество полей
                    if (parts.length != 7) {
                        System.err.println("Неверное количество полей в строке: " + line);
                        continue; // Пропустить эту строку
                    }

                    String isbn = parts[0].trim();
                    String title = parts[1].trim();
                    String authors = parts[2].trim(); // Список авторов
                    Book.Genre genre = Book.Genre.valueOf(parts[3].trim().toUpperCase()); // Приводим к верхнему регистру
                    int publicationYear = Integer.parseInt(parts[4].trim());
                    int pageCount = Integer.parseInt(parts[5].trim());
                    boolean available = Boolean.parseBoolean(parts[6].trim());

                    // Создание нового объекта Book
                    Book newBook = new Book(isbn, title, publicationYear, genre);
                    newBook.setPageCount(pageCount);
                    newBook.setAvailable(available);

                    // Добавление авторов
                    for (String author : authors.split(";")) {
                        newBook.addAuthor(author.trim()); // Добавляем каждого автора
                    }

                    // Добавление книги в библиотеку
                    if (addBook(newBook)) {
                        importedCount++; // Увеличиваем счетчик успешно импортированных книг
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка при парсинге строки: " + line + " - Неверный формат данных: " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Ошибка при доступе к элементам массива в строке: " + line + " - " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }

        return importedCount; // Возвращаем количество успешно импортированных книг
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