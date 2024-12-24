package data.model.RegexExceptions;

public class FieldOfWorkFormatException extends RegexValidationException {
    public FieldOfWorkFormatException(String message) {
        super("Field of work format error: " + message);
    }
}
