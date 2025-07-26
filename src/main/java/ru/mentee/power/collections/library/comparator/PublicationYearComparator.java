package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;
import java.util.Comparator;

public class PublicationYearComparator implements Comparator<Book> {

    @Override
    public int compare(Book b1, Book b2) {
        if (b1 == b2) {
            return 0;
        }
        if (b1 == null) {
            return 1;
        }
        if (b2 == null) {
            return -1;
        }
        // Сравнение по году публикации от новых к старым
        return Integer.compare(b2.getPublicationYear(), b1.getPublicationYear());
    }
}