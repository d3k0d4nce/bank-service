package ru.kishko.bankservice.user.exceptions;

public class LastContactException extends RuntimeException {

    public LastContactException() {
        super();
    }

    public LastContactException(String message) {
        super(message);
    }

    public LastContactException(String message, Throwable cause) {
        super(message, cause);
    }

    public LastContactException(Throwable cause) {
        super(cause);
    }

    protected LastContactException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
