package ru.mentee.power.exceptions.config.exception;

public class MissingConfigKeyException extends ConfigException{
    private final String missingKey;

    public MissingConfigKeyException(String message, String missingKey) {
        super(message);
        this.missingKey = missingKey;
    }

    public MissingConfigKeyException(String message, String missingKey, Throwable cause) {
        super(message, cause);
        this.missingKey = missingKey;
    }

    public String getMissingKey() {
        return missingKey;
    }
}
