package data.model.RegexExceptions;

public class NameFormatException extends RegexValidationException {
    public NameFormatException(String message) {
        super("Name format error: " + message);
    }
}
