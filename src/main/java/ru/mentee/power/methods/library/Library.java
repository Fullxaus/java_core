package ru.mentee.power.methods.library;

public class Library {
    private Book[] books;
    private int bookCount;

    /**
     * Создает новую библиотеку с заданной вместимостью
     *
     * @param capacity максимальное количество книг
     */
    public Library(int capacity) {
        this.books = new Book[capacity];
        this.bookCount = 0;
    }

    /**
     * Добавляет книгу в библиотеку
     *
     * @param book книга для добавления
     * @return true, если книга добавлена успешно, false, если библиотека переполнена
     */
    public boolean addBook(Book book) {
        if (bookCount >= books.length) {
            return false; // переполнена
        }
        books[bookCount++] = book;
        return true;
    }

    /**
     * Ищет книгу по названию
     *
     * @param title название книги
     * @return найденная книга или null, если книга не найдена
     */
    public Book findBookByTitle(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return books[i];
            }
        }
        return null;
    }

    /**
     * Выдает книгу читателю
     *
     * @param title название книги
     * @return true, если книга успешно выдана, false, если книга не найдена или уже выдана
     */
    public boolean checkoutBook(String title) {
        Book book = findBookByTitle(title);
        if (book == null || !book.isAvailable()) {
            return false;
        }
        book.setAvailable(false);
        return true;
    }

    /**
     * Возвращает книгу в библиотеку
     *
     * @param title название книги
     * @return true, если книга успешно возвращена, false, если книга не найдена или уже доступна
     */
    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);
        if (book == null || book.isAvailable()) {
            return false;
        }
        book.setAvailable(true);
        return true;
    }

    /**
     * Возвращает массив доступных книг
     *
     * @return массив доступных книг
     */
    public Book[] listAvailableBooks() {
        int count = 0;
        // Считаем, сколько книг доступно
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) {
                count++;
            }
        }
        // Собираем массив
        Book[] avail = new Book[count];
        int idx = 0;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].isAvailable()) {
                avail[idx++] = books[i];
            }
        }
        return avail;
    }

    /**
     * Возвращает массив выданных книг
     *
     * @return массив выданных книг
     */
    public Book[] listCheckedOutBooks() {
        int count = 0;
        // Считаем, сколько книг выдано
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isAvailable()) {
                count++;
            }
        }
        // Собираем массив
        Book[] checkedOut = new Book[count];
        int idx = 0;
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isAvailable()) {
                checkedOut[idx++] = books[i];
            }
        }
        return checkedOut;
    }
}
