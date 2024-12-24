package data.model.RegexExceptions;

public class PasswordFormatException extends RegexValidationException {
    public PasswordFormatException(String message) {
        super("Password format error: " + message);
    }
}
