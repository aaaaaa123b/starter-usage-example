package by.harlap.course.starter.exception;

public class UserBlockedException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "User is blocked. Session creation is prohibited";
    public static final String BLOCKED_USERNAME_MESSAGE = "User with username '%s' is blocked. Session creation is prohibited";

    public UserBlockedException() {
        super(DEFAULT_MESSAGE);
    }

    public UserBlockedException(String username) {
        super(BLOCKED_USERNAME_MESSAGE.formatted(username));
    }
}
