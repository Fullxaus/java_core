package ru.mentee.power.exceptions.config.exception;

public class ConfigException extends Exception{
    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
