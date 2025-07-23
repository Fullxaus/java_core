package ru.mentee.power.tdd.notes;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NoteService {
    private final Map<Integer, Note> notes = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    /**
     * Добавляет новую заметку.
     * @param title заголовок (не null)
     * @param text текст (не null)
     * @param tags набор тегов (может быть null)
     * @return созданная заметка
     */
    public Note addNote(String title, String text, Set<String> tags) {
        int id = nextId.getAndIncrement();
        Note note = new Note(id, title, text);
        if (tags != null) {
            tags.forEach(note::addTag);
        }
        notes.put(id, note);
        return note;
    }

    /**
     * Получить заметку по ID.
     */
    public Optional<Note> getNoteById(int id) {
        return Optional.ofNullable(notes.get(id));
    }

    /**
     * Получить все заметки (неизменяемый список).
     */
    public List<Note> getAllNotes() {
        return Collections.unmodifiableList(new ArrayList<>(notes.values()));
    }

    /**
     * Обновить заголовок и текст заметки.
     * @return true, если заметка найдена и обновлена
     */
    public boolean updateNoteText(int id, String newTitle, String newText) {
        Note note = notes.get(id);
        if (note == null) return false;
        note.setTitle(newTitle);
        note.setText(newText);
        return true;
    }

    /**
     * Добавить тег к заметке.
     * @return true, если заметка найдена и тег добавлен
     */
    public boolean addTagToNote(int id, String tag) {
        Note note = notes.get(id);
        if (note == null) return false;
        note.addTag(tag);
        return true;
    }

    /**
     * Удалить тег у заметки.
     * @return true, если заметка найдена и тег удалён
     */
    public boolean removeTagFromNote(int id, String tag) {
        Note note = notes.get(id);
        if (note == null) return false;
        return note.removeTag(tag);
    }

    /**
     * Удалить заметку по ID.
     * @return true, если заметка найдена и удалена
     */
    public boolean deleteNote(int id) {
        return notes.remove(id) != null;
    }

    /**
     * Поиск по тексту (заголовок или тело). Регистронезависимый.
     * @param query строка поиска
     * @return список найденных заметок
     */
    public List<Note> findNotesByText(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String q = query.toLowerCase();
        return notes.values().stream()
                .filter(n -> n.getTitle().toLowerCase().contains(q)
                        || n.getText().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    /**
     * Поиск по тегам: заметка должна содержать все указанные теги (без учёта регистра).
     * @param searchTags набор тегов
     * @return список найденных заметок
     */
    public List<Note> findNotesByTags(Set<String> searchTags) {
        if (searchTags == null || searchTags.isEmpty()) {
            return List.of();
        }
        Set<String> normalized = searchTags.stream()
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return notes.values().stream()
                .filter(n -> n.getTags().containsAll(normalized))
                .collect(Collectors.toList());
    }

    /**
     * Список всех уникальных тегов (в нижнем регистре).
     */
    public Set<String> getAllTags() {
        return notes.values().stream()
                .flatMap(n -> n.getTags().stream())
                .collect(Collectors.toSet());
    }
}
