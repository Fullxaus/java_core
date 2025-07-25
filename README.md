Добавил следующие классы:
1  Личная карточка студента
2  Калькулятор типов
3  Строковые операции
4  Константы и области видимости
5  Конвертер температур

Создал пакет conditions в котором находятся следующие классы:LogicalTrainer,RentCar,TaxCalculator,TheaterTickets,TrafficLight,WeekdayDeterminer.
Класс LogicalTrainer помогает узнать ,какие вещи любит пользователь.
Класс RentCar помогает узнать,может ли человек арендовать автомобиль.
Класс TaxCalculator помогает узнать,какой налог должен заплатить гражданин,исходя из суммы доходов.
Класс TheaterTickets помогает узнать цену билета,исходя из того,является человек работающим,студентом или пенсионером.
Класс TrafficLight помогает узнать, как нужно реагировать на показания светофора,находясь на проезжей части.
Класс WeekdayDeterminer показывает день в недели.
Данные программы работают с помощью объекта Scanner

Создал два класса ArrayListAnalyzer и LinkedListAnalyzer.
Хотя классы ArrayList и LinkedList реализуют один интерфейс List, они имеют свои отличия.

 Доступ по индексу
        ArrayList: доступ к элементу по индексу через get(i) и set(i, e) выполняется за O(1).
        LinkedList: доступ по индексу медленный (O(n)), поэтому вместо этого используют итераторы.
Добавление/удаление на концах
        ArrayList: методы add(…) в конец амортизированно O(1), а вставка/удаление в середине O(n) (сдвиг элементов).
        LinkedList: методы addFirst(), addLast(), removeFirst(), removeLast() работают за O(1).
Проход по списку
        ArrayList: традиционные for-цикл с индексом — самый быстрый способ.
        LinkedList: лучший способ — использовать Iterator или for-each, чтобы не делать многократные переходы по ссылкам.
Я использовал следующие особенности LinkedList для оптимизации работы методов:
  1)Итераторы (iterator()) вместо get(index) для одного прохода по элементам.
  2)Специальные методы:
        -addFirst()/addLast() для быстрого наращивания результирующих списков.
        -removeFirst()/removeLast() для реализации циклического сдвига без полного копирования.
  При необходимости хранения промежуточных данных (например, при реверсе) сразу работаем с новым LinkedList, чтобы вставлять элементы «в начало» за O(1).
На LinkedList работают быстрее следующие методы:
    1)Добавление и удаление на концах (addFirst, addLast, removeFirst, removeLast) — O(1).
    2)Итераторный проход без перераспределения массива в середине.
На ArrayList работают быстрее следующие методы:
    1)get(i), set(i, e) — доступ по индексу за O(1).
    2)Проход цикла с индексом минимизирует проверку границ и значительно быстрее при большом количестве элементов.

  В реализации для ArrayList методы спроектированы вокруг доступа по индексу и объёмных операций над массивом:
        Фильтрация, поиск максимума, разбиение на части, проверка палиндрома — все это делается циклом for (int i = 0; i < size; i++).

   В реализации для LinkedList главная задача — избежать индексного доступа и использовать преимущества двусвязных узлов:
        Везде применяются итераторы (for-each или Iterator<T>) для последовательного обхода.
        Добавление/удаление в середине или на концах выполняется через специальные методы, не требующие сдвига элементов.
-----------------------------------------------------------------------------------------------------------------------------------------
Создал проект Library для работы с коллекциями. Проект показывает взаимосвязь между читателем, библиотекой и книгами. Для данного проекта использовались коллекции Map, Set, List.
Так же создал отдельные компараторы. 
Для их реализации были созданы следующие классы AvailabilityComparator, GenreAndTitleComparator, PublicationYearComparator и TitleComparator. Данные компараторы необходимы для сортировки книг.
Для оптимизации использовались коллекции Map для быстрого поиска нужных книг,специализированные итераторы для фильтрации данных.
Enum применяется для классификации книг по жанрам. Такая классификация обеспечивает быстрое добавление и быстрый доступ к книгам определенного жанра.
Структура проекта 
Класс Book реализует важные характеристики книги, так же в этом классе находится enum для указания жанра книг.
Класс Borrowing представляет собой запись о выдаче книги читателю в библиотеке. Этот класс хранит информацию о конкретном факте выдачи книги и поддерживает базовые операции, необходимые для управления процессом выдачи и возврата книг.
Класс Reader предоставляет данные читателя и реализует основные операции с читателем.
Класс LibraryManager играет центральную роль в управлении библиотекой. Он отвечает за координацию всех функций библиотеки, включая управление книгами, читателями, выдачей и возвратом книг, а также ведение статистики и отчетности.
Класс LibraryDemo служит демонстрационным инструментом для иллюстрации работы основной функциональности библиотеки. Он предназначен для практической демонстрации работы основных компонентов системы управления библиотекой и демонстрирует, как функционируют классы LibraryManager, Book, Reader и Borrowing.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Создал класс NoteServise для различной работы с заметками.
Базовым классом стал класс Note. В нем отражены основные части заметки, такие как заголовок,текст и id заметки.
Для работы с классом Note был создан класс NoteServise, в нем находится конструктор Note, объекты которого затем попадает в коллекцию Map notes. Id заметки помещается в переменную nextId.
Работа с классом NoteServise реализована через различные методы,перечислим несколько из них.
Метод getNoteById -позволяет получить id заметки.
Метод updateNoteText - позволяет поменять текст заметки.
Метод addTagToNote - позволяет поменять заголовок заметки.
Метод removeTagFromNote - позволяет удалить заголовок заметки.
Метод findNotesByText - позволяет найти заметку по тексту.
Метод findNotesByTags -позволяет найти заметку по тегу.
Метод getAllTags - позволяет получить все заголовки.
Таким образом мы видим, что класс NoteServise отражает много аспектов работы с заметками.
