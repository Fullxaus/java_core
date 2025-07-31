package ru.mentee.power.tdd.notes;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public final class NoteService {
    private final Map<Integer, Note> notes = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Note addNote(String title, String text, Set<String> tags) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(text,  "text must not be null");

        int id = nextId.getAndIncrement();
        Note note = new Note(id, title, text);
        if (tags != null) {
            tags.forEach(note::addTag);
        }
        notes.put(id, note);
        return note;
    }

    public Optional<Note> getNoteById(int id) {
        return Optional.ofNullable(notes.get(id));
    }

    public List<Note> getAllNotes() {
        // По-прежнему возвращаем неизменяемый список
        return Collections.unmodifiableList(new ArrayList<>(notes.values()));
    }

    public boolean updateNoteText(int id, String newTitle, String newText) {
        Note note = notes.get(id);
        if (note == null) {
            return false;
        }
        note.setTitle(newTitle);
        note.setText(newText);
        return true;
    }

    public boolean addTagToNote(int id, String tag) {
        Note note = notes.get(id);
        if (note == null) {
            return false;
        }
        note.addTag(tag);
        return true;
    }

    public boolean removeTagFromNote(int id, String tag) {
        Note note = notes.get(id);
        if (note == null) {
            return false;
        }
        return note.removeTag(tag);
    }

    public boolean deleteNote(int id) {
        return notes.remove(id) != null;
    }

    public List<Note> findNotesByText(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String q = query.toLowerCase(Locale.ROOT);
        // Используем Stream.toList()
        return notes.values().stream()
                .filter(n -> n.getTitle().toLowerCase(Locale.ROOT).contains(q)
                        || n.getText().toLowerCase(Locale.ROOT).contains(q))
                .toList();
    }

    public List<Note> findNotesByTags(Set<String> searchTags) {
        if (searchTags == null || searchTags.isEmpty()) {
            return List.of();
        }
        Set<String> normalized = searchTags.stream()
                .filter(Objects::nonNull)
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        // И здесь заменили collect(Collectors.toList()) на toList()
        return notes.values().stream()
                .filter(n -> n.getTags().containsAll(normalized))
                .toList();
    }

    public Set<String> getAllTags() {
        // Здесь нужен Set, поэтому collect(Collectors.toSet()) оставляем
        return notes.values().stream()
                .flatMap(n -> n.getTags().stream())
                .collect(Collectors.toSet());
    }
}
