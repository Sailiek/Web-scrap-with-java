package data.model.RegexExceptions;

public class UserTypeFormatException extends RegexValidationException {
    public UserTypeFormatException(String message) {
        super("User type format error: " + message);
    }
}
