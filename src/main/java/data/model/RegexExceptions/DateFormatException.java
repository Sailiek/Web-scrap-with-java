package data.model.RegexExceptions;

public class DateFormatException extends RegexValidationException {
    public DateFormatException(String message) {
        super("Date format error: " + message);
    }
}
