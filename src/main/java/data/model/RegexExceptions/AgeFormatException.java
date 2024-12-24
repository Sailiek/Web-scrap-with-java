package data.model.RegexExceptions;

public class AgeFormatException extends RegexValidationException {
    public AgeFormatException(String message) {
        super("Age format error: " + message);
    }
}
