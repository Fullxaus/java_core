package ru.mentee.power.tdd.notes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NoteServiceTest {
    private NoteService service;

    @BeforeEach
    void setUp() {
        service = new NoteService();
    }

    @Test
    void addNote_shouldAssignIdStoreAndReturnNote() {
        Note created = service.addNote("Заголовок", "Текст",
                Set.of("one", "two"));
        assertNotNull(created);
        assertTrue(created.getId() > 0);
        assertEquals("Заголовок", created.getTitle());
        assertEquals("Текст", created.getText());
        assertEquals(Set.of("one", "two"), created.getTags());

        Optional<Note> fetched = service.getNoteById(created.getId());
        assertTrue(fetched.isPresent());
        assertEquals(created, fetched.get());
    }

    @Test
    void getAllNotes_shouldReturnAllAdded() {
        Note n1 = service.addNote("A","1", null);
        Note n2 = service.addNote("B","2", Set.of("t"));
        List<Note> all = service.getAllNotes();
        assertEquals(2, all.size());
        assertTrue(all.contains(n1));
        assertTrue(all.contains(n2));
    }

    @Test
    void updateNoteText_existingId_shouldReturnTrueAndUpdate() {
        Note n = service.addNote("Old","Old", null);
        boolean ok = service.updateNoteText(n.getId(), "New","New");
        assertTrue(ok);
        Note fetched = service.getNoteById(n.getId()).get();
        assertEquals("New", fetched.getTitle());
        assertEquals("New", fetched.getText());
    }

    @Test
    void updateNoteText_nonExisting_shouldReturnFalse() {
        assertFalse(service.updateNoteText(999, "x","y"));
    }

    @Test
    void addTagToNote_existing_shouldReturnTrueAndAdd() {
        Note n = service.addNote("T","X", Set.of("a"));
        boolean ok = service.addTagToNote(n.getId(), "B");
        assertTrue(ok);
        assertEquals(Set.of("a","b"), service.getNoteById(n.getId()).get().getTags());
    }

    @Test
    void addTagToNote_nonExisting_shouldReturnFalse() {
        assertFalse(service.addTagToNote(123, "tag"));
    }

    @Test
    void removeTagFromNote_existing_shouldReturnTrueAndRemove() {
        Note n = service.addNote("T","X", Set.of("one","two"));
        boolean ok = service.removeTagFromNote(n.getId(), "ONE");
        assertTrue(ok);
        assertEquals(Set.of("two"), service.getNoteById(n.getId()).get().getTags());
    }

    @Test
    void removeTagFromNote_nonExisting_shouldReturnFalse() {
        assertFalse(service.removeTagFromNote(123, "tag"));
    }

    @Test
    void deleteNote_existing_shouldReturnTrueAndDelete() {
        Note n = service.addNote("A","B", null);
        assertTrue(service.deleteNote(n.getId()));
        assertFalse(service.getNoteById(n.getId()).isPresent());
    }

    @Test
    void deleteNote_nonExisting_shouldReturnFalse() {
        assertFalse(service.deleteNote(999));
    }

    @Test
    void findNotesByText_shouldBeCaseInsensitiveInTitleAndText() {
        service.addNote("Java","ABC", null);
        service.addNote("X","learn java streams", null);
        service.addNote("Y","nothing here", null);

        List<Note> res1 = service.findNotesByText("JAVA");
        assertEquals(2, res1.size());

        List<Note> res2 = service.findNotesByText("streams");
        assertEquals(1, res2.size());
        assertEquals("X", res2.get(0).getTitle());
    }

    @Test
    void findNotesByText_nullOrBlankQuery_shouldReturnEmpty() {
        service.addNote("A","B", null);
        assertTrue(service.findNotesByText(null).isEmpty());
        assertTrue(service.findNotesByText(" ").isEmpty());
    }

    @Test
    void findNotesByTags_shouldReturnOnlyNotesContainingAllTags() {
        service.addNote("N1","T1", Set.of("a","b","c"));
        service.addNote("N2","T2", Set.of("a","c"));
        service.addNote("N3","T3", Set.of("b","c"));

        List<Note> found = service.findNotesByTags(Set.of("A","C"));
        assertEquals(2, found.size());
        assertTrue(found.stream().anyMatch(n -> n.getTitle().equals("N1")));
        assertTrue(found.stream().anyMatch(n -> n.getTitle().equals("N2")));
    }

    @Test
    void findNotesByTags_nullOrEmptySearch_shouldReturnEmpty() {
        service.addNote("A","B", Set.of("t"));
        assertTrue(service.findNotesByTags(null).isEmpty());
        assertTrue(service.findNotesByTags(Set.of()).isEmpty());
    }

    @Test
    void getAllTags_shouldReturnUniqueLowercaseTags() {
        service.addNote("A","B", Set.of("One","Two"));
        service.addNote("C","D", Set.of("one","three"));
        Set<String> tags = service.getAllTags();
        assertEquals(Set.of("one","two","three"), tags);
    }
}
