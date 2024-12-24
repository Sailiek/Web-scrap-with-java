package data.model.RegexExceptions;

public class EmailFormatException extends RegexValidationException {
    public EmailFormatException(String message) {
        super("Email format error: " + message);
    }
}
