package ru.mentee.power.methods.library;

public class LibraryDemo {
    public static void main(String[] args) {
        // Создаем библиотеку с вместимостью на 5 книг
        Library library = new Library(5);

        // Создаем объекты-книги с указанными произведениями
        Book book1 = new Book("Warhammer: Horus Rising", "Dan Abnett", 2006);
        Book book2 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954);
        Book book3 = new Book("War and Peace", "Leo Tolstoy", 1869);
        Book book4 = new Book("Crime and Punishment", "Fyodor Dostoevsky", 1866);
        Book book5 = new Book("The Master and Margarita", "Mikhail Bulgakov", 1967);

        // Добавляем книги в библиотеку
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        // Выводим список всех доступных книг
        System.out.println("Available books in the library:");
        for (Book b : library.listAvailableBooks()) {
            System.out.println(b);
        }

        // Выдаем несколько книг
        System.out.println("\nChecking out 'Warhammer: Horus Rising' and 'War and Peace'...");
        library.checkoutBook(book1.getTitle());
        library.checkoutBook(book3.getTitle());

        // Выводим списки доступных и выданных книг
        System.out.println("\nAvailable books after checkout:");
        for (Book b : library.listAvailableBooks()) {
            System.out.println(b);
        }
        System.out.println("\nChecked out books:");
        for (Book b : library.listCheckedOutBooks()) {
            System.out.println(b);
        }

        // Возвращаем книгу
        System.out.println("\nReturning 'Warhammer: Horus Rising'...");
        library.returnBook(book1.getTitle());

        // Итоговый вывод
        System.out.println("\nAvailable books after return:");
        for (Book b : library.listAvailableBooks()) {
            System.out.println(b);
        }
        System.out.println("\nChecked out books after return:");
        for (Book b : library.listCheckedOutBooks()) {
            System.out.println(b);
        }
    }
}