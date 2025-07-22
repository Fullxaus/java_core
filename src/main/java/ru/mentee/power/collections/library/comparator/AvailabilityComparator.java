package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class AvailabilityComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        if (b1 == b2) return 0;
        if (b1 == null) return 1;
        if (b2 == null) return -1;
        // доступные (true) должны идти раньше недоступных (false)
        return Boolean.compare(b2.isAvailable(), b1.isAvailable());
    }
}