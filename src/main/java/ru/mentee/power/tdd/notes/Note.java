package ru.mentee.power.tdd.notes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Note {
    private final int id;
    private String title;
    private String text;
    private final LocalDate creationDate;
    private final Set<String> tags;

    public Note(int id, String title, String text) {
        if (title == null) throw new IllegalArgumentException("title cannot be null");
        if (text == null)  throw new IllegalArgumentException("text cannot be null");

        this.id = id;
        this.title = title;
        this.text = text;
        this.creationDate = LocalDate.now();
        this.tags = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setTitle(String title) {
        if (title == null) throw new IllegalArgumentException("title cannot be null");
        this.title = title;
    }

    public void setText(String text) {
        if (text == null) throw new IllegalArgumentException("text cannot be null");
        this.text = text;
    }

    public void addTag(String tag) {
        if (tag == null || tag.isBlank()) throw new IllegalArgumentException("tag cannot be null or empty");
        tags.add(tag.toLowerCase());
    }

    public boolean removeTag(String tag) {
        if (tag == null) return false;
        return tags.remove(tag.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return id == note.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
