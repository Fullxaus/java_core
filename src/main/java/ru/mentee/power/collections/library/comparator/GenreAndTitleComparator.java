package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;

import java.util.Comparator;

public class GenreAndTitleComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();

    @Override
    public int compare(Book b1, Book b2) {
        if (b1 == b2) return 0;
        if (b1 == null) return 1;
        if (b2 == null) return -1;

        // Сначала сравниваем жанры (Enum.compareTo)
        if (b1.getGenre() != null && b2.getGenre() != null) {
            int genreCompare = b1.getGenre().compareTo(b2.getGenre());
            if (genreCompare != 0) {
                return genreCompare;
            }
        } else if (b1.getGenre() == null && b2.getGenre() != null) {
            return 1;
        } else if (b1.getGenre() != null && b2.getGenre() == null) {
            return -1;
        }
        // Если жанры равны — по названию
        return titleComparator.compare(b1, b2);
    }
}
