package by.harlap.course.starter.exception;

public class SessionNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Session not found";

    public SessionNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public SessionNotFoundException(String message) {
        super(message);
    }
}
