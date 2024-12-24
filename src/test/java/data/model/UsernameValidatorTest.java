package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsernameValidatorTest {
    @Test
    void validateUsername_ValidUsernames() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateUsername("john_doe"));
        assertDoesNotThrow(() -> UserValidator.validateUsername("user123"));
        assertDoesNotThrow(() -> UserValidator.validateUsername("test_user_123"));
    }

    @Test
    void validateUsername_InvalidUsernames() {
        assertThrows(UsernameFormatException.class, () -> UserValidator.validateUsername("jo")); // Too short
        assertThrows(UsernameFormatException.class, () -> UserValidator.validateUsername("user@123")); // Invalid character
        assertThrows(UsernameFormatException.class, () -> UserValidator.validateUsername("user name")); // Space not allowed
    }
}
