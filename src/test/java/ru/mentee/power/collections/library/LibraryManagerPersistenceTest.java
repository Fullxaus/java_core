package ru.mentee.power.collections.library;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import ru.mentee.power.collections.library.Book;
import ru.mentee.power.collections.library.LibraryManager;
import ru.mentee.power.collections.library.Reader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты сохранения/загрузки состояния LibraryManager")
public class LibraryManagerPersistenceTest {

    @TempDir
    Path tempDir;

    private Path stateFilePath;
    private LibraryManager managerToSave;

    @BeforeEach
    void setUp() {
        stateFilePath = tempDir.resolve("library_test_state.ser");
        managerToSave = new LibraryManager();

        // Добавление книг и читателей для тестов
        managerToSave.addBook(new Book("978-3-16-148410-0", "Java: The Complete Reference", 2023, Book.Genre.SCIENCE));
        managerToSave.addBook(new Book("978-1-491-91205-8", "Clean Code", 2008, Book.Genre.SCIENCE));
        managerToSave.addReader(new Reader("RDR001", "Иван Петров", "ivan@example.com", Reader.ReaderCategory.STUDENT));
    }

    @Test
    @DisplayName("Должен сохранять и загружать непустое состояние")
    void shouldSaveAndLoadNonEmptyState() throws IOException, ClassNotFoundException {
        // Подготовка данных
        Book book1 = new Book("978-3-16-148410-0", "Java: The Complete Reference", 2023, Book.Genre.SCIENCE);
        Book book2 = new Book("978-1-491-91205-8", "Clean Code", 2008, Book.Genre.SCIENCE);
        managerToSave.addBook(book1);
        managerToSave.addBook(book2);

        Reader reader1 = new Reader("RDR001", "Иван Петров", "ivan@example.com", Reader.ReaderCategory.STUDENT);
        Reader reader2 = new Reader("RDR002", "Анна Смирнова", "anna@example.com", Reader.ReaderCategory.TEACHER);
        managerToSave.addReader(reader1);
        managerToSave.addReader(reader2);

        // When
        managerToSave.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Then
        assertThat(loadedManager).isNotNull();
        assertThat(loadedManager.getAllBooks().size()).isEqualTo(managerToSave.getAllBooks().size());
        assertThat(loadedManager.getAllReaders().size()).isEqualTo(managerToSave.getAllReaders().size());
        assertThat(loadedManager.getBorrowings().size()).isEqualTo(managerToSave.getBorrowings().size());


        // Проверка, что transient поля не null и содержат данные
        loadedManager.rebuildIndexes(); // Восстановление индексов
        assertThat(loadedManager.getBooksByGenre()).isNotNull();
        assertThat(loadedManager.getBooksByGenre()).isNotEmpty();
    }

    @Test
    @DisplayName("Должен сохранять и загружать пустое состояние")
    void shouldSaveAndLoadEmptyState() throws IOException, ClassNotFoundException {
        // Given
        LibraryManager emptyManager = new LibraryManager();

        // When
        emptyManager.saveLibraryState(stateFilePath.toString());
        LibraryManager loadedManager = LibraryManager.loadLibraryState(stateFilePath.toString());

        // Then
        assertThat(loadedManager).isNotNull();
        assertThat(loadedManager.getALLBooks()).isEmpty();
        assertThat(loadedManager.getALLReaders()).isEmpty();
        assertThat(loadedManager.getBorrowings()).isEmpty();
    }

    @Test
    @DisplayName("loadLibraryState должен возвращать null для несуществующего файла")
    void loadShouldReturnNullForNonExistentFile() throws IOException, ClassNotFoundException {
        // Given: файл не существует (не вызываем save)

        // When
        LibraryManager loadedManager = LibraryManager.loadLibraryState(tempDir.resolve("non_existent_file.ser").toString());

        // Then
        assertThat(loadedManager).isNull();
    }
}

