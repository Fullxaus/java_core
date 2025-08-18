package ru.mentee.power.collections.library;

import java.io.File;

public class LibraryPersistenceDemo {

    private static final String STATE_FILE = "library_state.dat";
    private static final String BOOKS_CSV_FILE = "books.csv";

    public static void main(String[] args) {
        try {
            LibraryManager libraryManager = LibraryManager.loadLibraryState(STATE_FILE);

            // Проверка, если библиотека не загружена
            if (libraryManager == null) {
                libraryManager = new LibraryManager();
                libraryManager.initializeData(); // Инициализация начальных данных

                // Добавление книг и читателей для первого запуска
                libraryManager.addBook(new Book(
                        "978-3-16-148410-0",
                        "Java: The Complete Reference",
                        2023,
                        Book.Genre.SCIENCE));
                libraryManager.addBook(new Book(
                        "978-1-491-91205-8",
                        "Clean Code",
                        2008,
                        Book.Genre.SCIENCE));
                libraryManager.addReader(new Reader(
                        "RDR001",
                        "Иван Петров",
                        "ivan@example.com",
                        Reader.ReaderCategory.STUDENT));
                libraryManager.addReader(new Reader(
                        "RDR002",
                        "Анна Смирнова",
                        "anna@example.com",
                        Reader.ReaderCategory.TEACHER));

                // Вывод информации о добавленных книгах
                System.out.println("Добавлены книги:");
                for (Book book : libraryManager.getAllBooks()) {
                    System.out.println(" - " + book.getTitle());
                }
            }

            // Далее идёт демонстрация операций и вывод статистики
            libraryManager.printStatistics(); // Вывод статистики
            libraryManager.borrowBook("978-3-16-148410-0", "RDR001", 14); // Выдача книги

            // Экспорт книг в CSV
            libraryManager.exportBooksToCsv(BOOKS_CSV_FILE, " ");

            // Импорт книг из CSV
            File importFile = new File(BOOKS_CSV_FILE);
            if (importFile.exists()) {
                libraryManager.importBooksFromCsv(BOOKS_CSV_FILE, " ", true);
            }

            // Вывод статистики после импорта
            libraryManager.printStatistics();

            // Сохранение состояния библиотеки
            libraryManager.saveLibraryState(STATE_FILE);
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}