package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {
    @Test
    void validateUserPassword_ValidPasswords() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateUserPassword("Password1!"));
        assertDoesNotThrow(() -> UserValidator.validateUserPassword("Complex1@Password"));
        assertDoesNotThrow(() -> UserValidator.validateUserPassword("Abcd123$efgh"));
    }

    @Test
    void validateUserPassword_InvalidPasswords() {
        assertThrows(PasswordFormatException.class, () -> UserValidator.validateUserPassword("weak")); // Too short
        assertThrows(PasswordFormatException.class, () -> UserValidator.validateUserPassword("NoSpecialChar1")); // No special char
        assertThrows(PasswordFormatException.class, () -> UserValidator.validateUserPassword("nouppercasechar1!")); // No uppercase
    }
}
