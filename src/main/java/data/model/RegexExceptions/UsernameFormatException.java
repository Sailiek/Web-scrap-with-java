package data.model.RegexExceptions;

public class UsernameFormatException extends RegexValidationException {
    public UsernameFormatException(String message) {
        super("Username format error: " + message);
    }
}
