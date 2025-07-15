package ru.mentee.power.exceptions.config.exception;

public class InvalidConfigValueException extends ConfigException{

    private final String key;
    private final String value;

    /**
     * @param message текст ошибки
     * @param key     ключ, значение которого неверно
     * @param value   само неверное значение
     */
    public InvalidConfigValueException(String message, String key, String value) {
        super(message);
        this.key = key;
        this.value = value;
    }

    /**
     * @param message текст ошибки
     * @param key     ключ, значение которого неверно
     * @param value   само неверное значение
     * @param cause   причина
     */
    public InvalidConfigValueException(String message, String key, String value, Throwable cause) {
        super(message, cause);
        this.key = key;
        this.value = value;
    }

    /**
     * Возвращает ключ, для которого значение оказалось неверным.
     */
    public String getKey() {
        return key;
    }

    /**
     * Возвращает само неверное значение.
     */
    public String getValue() {
        return value;
    }

}
