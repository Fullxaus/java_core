package ru.mentee.power.tdd.notes;

/**
 * Бросается в сервисе при ошибках уровня «I/O», сериализации и т.п.
 */
public class NoteServiceException extends RuntimeException {
    public NoteServiceException() {
        super();
    }

    public NoteServiceException(String message) {
        super(message);
    }

    public NoteServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteServiceException(Throwable cause) {
        super(cause);
    }
}
