package ru.mentee.power.collections.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LibraryDemo {
    public static void main(String[] args) {
        // 1. Создаем экземпляр LibraryManager
        LibraryManager manager = new LibraryManager();

        // 2. Добавляем несколько книг
        Book book1 = new Book("978-3-16-148410-0", "Java: The Complete Reference", 2023, Book.Genre.SCIENCE);
        book1.addAuthor("Herbert Schildt");
        manager.addBook(book1);

        Book book2 = new Book("978-1-491-91205-8", "Clean Code", 2008, Book.Genre.SCIENCE);
        book2.addAuthor("Robert C. Martin");
        manager.addBook(book2);

        Book book3 = new Book("978-0-321-35668-0", "Refactoring: Improving the Design of Existing Code", 1999, Book.Genre.SCIENCE);
        book3.addAuthor("Martin Fowler");
        manager.addBook(book3);

        // 3. Добавляем нескольких читателей
        Reader reader1 = new Reader("RDR001", "Иван Петров", "ivan@example.com", Reader.ReaderCategory.STUDENT);
        manager.addReader(reader1);

        Reader reader2 = new Reader("RDR002", "Анна Смирнова", "anna@example.com", Reader.ReaderCategory.TEACHER);
        manager.addReader(reader2);

        // 4. Выдаем книги читателям
        manager.borrowBook("978-3-16-148410-0", "RDR001", 14); // Иван берет первую книгу на две недели
        manager.borrowBook("978-1-491-91205-8", "RDR002", 7);  // Анна берет вторую книгу на неделю

        // 5. Некоторые книги возвращаем
        manager.returnBook("978-3-16-148410-0", "RDR001"); // Иван возвращает свою книгу

        // 6. Простой поиск и фильтрация книг
        System.out.println("\nСписок всех книг:");
        manager.getAllBooks().forEach(System.out::println);

        System.out.println("\nДоступные книги:");
        manager.getAvailableBooks().forEach(System.out::println);

        System.out.println("\nПоиск книг по названию \"Java\":");
        manager.searchBooksByTitle("Java").forEach(System.out::println);

        // 7. Демонстрация сортировок
        System.out.println("\nОтсортированы по названию:");
        manager.sortBooksByTitle(new ArrayList<>(manager.getAllBooks())).forEach(System.out::println);

        System.out.println("\nОтсортированы по году публикации:");
        manager.sortBooksByPublicationYear(new ArrayList<>(manager.getAllBooks())).forEach(System.out::println);

        // 8. Демонстрация работы с итераторами
        System.out.println("\nПеречислим книги жанра SCIENCE:");
        manager.getBooksByGenre(Book.Genre.SCIENCE).forEach(System.out::println);

        // 9. Генерация и просмотр статистики
        System.out.println("\nТоп популярных книг (всего одна выдача):");
        manager.getTopPopularBooks(3).forEach(entry -> System.out.printf("%s (%d выдач)\n", entry.getKey(), entry.getValue()));

        System.out.println("\nТоп активных читателей:");
        manager.getTopActiveReaders(3).forEach(entry -> System.out.printf("%s (%d выдач)\n", entry.getKey(), entry.getValue()));

        System.out.println("\nКоличество книг по жанрам:");
        manager.getGenreStatistics().entrySet().forEach(entry -> System.out.printf("%s: %d\n", entry.getKey(), entry.getValue()));
    }
}