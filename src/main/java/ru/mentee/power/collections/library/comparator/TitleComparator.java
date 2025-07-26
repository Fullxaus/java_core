package ru.mentee.power.collections.library.comparator;

import ru.mentee.power.collections.library.Book;
import java.util.Comparator;

public class TitleComparator implements Comparator<Book> {

    @Override
    public int compare(Book b1, Book b2) {
        // Сначала проверяем сами объекты
        if (b1 == b2) {
            return 0;
        }
        if (b1 == null) {
            return 1;
        }
        if (b2 == null) {
            return -1;
        }

        String t1 = b1.getTitle();
        String t2 = b2.getTitle();
        // Выносим null в конец
        if (t1 == t2) {
            return 0;
        }
        if (t1 == null) {
            return 1;
        }
        if (t2 == null) {
            return -1;
        }
        // Собственно сравниваем
        return t1.compareToIgnoreCase(t2);
    }

}
