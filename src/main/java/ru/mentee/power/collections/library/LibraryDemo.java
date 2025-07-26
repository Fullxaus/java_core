package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Пример демонстрации возможностей {@link LibraryManager}.
 * Создаются книги и читатели, выполняются операции выдачи/возврата,
 * демонстрируются фильтрация, сортировка и сбор статистики.
 */
public class LibraryDemo {

    /**
     * Точка входа в демонстрацию.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        // 1. Создаём менеджер библиотеки
        LibraryManager manager = new LibraryManager();

        // 2. Добавляем книги
        Book book1 = new Book(
                "978-3-16-148410-0",
                "Java: The Complete Reference",
                2023,
                Book.Genre.SCIENCE);
        book1.addAuthor("Herbert Schildt");
        manager.addBook(book1);

        Book book2 = new Book(
                "978-1-491-91205-8",
                "Clean Code",
                2008,
                Book.Genre.SCIENCE);
        book2.addAuthor("Robert C. Martin");
        manager.addBook(book2);

        Book book3 = new Book(
                "978-0-321-35668-0",
                "Refactoring: Improving the Design of Existing Code",
                1999,
                Book.Genre.SCIENCE);
        book3.addAuthor("Martin Fowler");
        manager.addBook(book3);

        // 3. Регистрируем читателей
        Reader reader1 = new Reader(
                "RDR001",
                "Иван Петров",
                "ivan@example.com",
                Reader.ReaderCategory.STUDENT);
        manager.addReader(reader1);

        Reader reader2 = new Reader(
                "RDR002",
                "Анна Смирнова",
                "anna@example.com",
                Reader.ReaderCategory.TEACHER);
        manager.addReader(reader2);

        // 4. Выдаём книги
        manager.borrowBook("978-3-16-148410-0", "RDR001", 14);
        manager.borrowBook("978-1-491-91205-8", "RDR002", 7);

        // 5. Возвращаем книгу
        manager.returnBook("978-3-16-148410-0", "RDR001");

        // 6. Показываем все книги
        System.out.println();
        System.out.println("Список всех книг:");
        manager.getAllBooks().forEach(System.out::println);

        System.out.println();
        System.out.println("Доступные книги:");
        manager.getAvailableBooks().forEach(System.out::println);

        System.out.println();
        System.out.println("Поиск книг по названию \"Java\":");
        manager.searchBooksByTitle("Java").forEach(System.out::println);

        // 7. Демонстрация сортировок
        System.out.println();
        System.out.println("Отсортированы по названию:");
        List<Book> allBooks = new ArrayList<>(manager.getAllBooks());
        manager.sortBooksByTitle(allBooks).forEach(System.out::println);

        System.out.println();
        System.out.println("Отсортированы по году публикации:");
        manager.sortBooksByPublicationYear(allBooks).forEach(System.out::println);

        // 8. Демонстрация фильтрации по жанру
        System.out.println();
        System.out.println("Книги жанра SCIENCE:");
        manager.getBooksByGenre(Book.Genre.SCIENCE).forEach(System.out::println);

        // 9. Демонстрация статистики
        System.out.println();
        System.out.println("Топ популярных книг:");
        manager.getTopPopularBooks(3)
                .forEach(entry -> System.out.printf(
                        "%s (%d выдач)%n",
                        entry.getKey(),
                        entry.getValue()));

        System.out.println();
        System.out.println("Топ активных читателей:");
        manager.getTopActiveReaders(3)
                .forEach(entry -> System.out.printf(
                        "%s (%d выдач)%n",
                        entry.getKey(),
                        entry.getValue()));

        System.out.println();
        System.out.println("Количество книг по жанрам:");
        for (Map.Entry<Book.Genre, Integer> entry : manager.getGenreStatistics().entrySet()) {
            System.out.printf("%s: %d%n", entry.getKey(), entry.getValue());
        }
    }
}