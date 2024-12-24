package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {
    @Test
    void validateUserEmail_ValidEmails() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateUserEmail("test@example.com"));
        assertDoesNotThrow(() -> UserValidator.validateUserEmail("user.name+tag@example.co.uk"));
        assertDoesNotThrow(() -> UserValidator.validateUserEmail("user123@domain.com"));
    }

    @Test
    void validateUserEmail_InvalidEmails() {
        assertThrows(EmailFormatException.class, () -> UserValidator.validateUserEmail("invalid.email"));
        assertThrows(EmailFormatException.class, () -> UserValidator.validateUserEmail("@domain.com"));
        assertThrows(EmailFormatException.class, () -> UserValidator.validateUserEmail("user@.com"));
    }
}
