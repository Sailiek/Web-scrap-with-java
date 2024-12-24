package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrenomValidatorTest {
    @Test
    void validatePrenom_ValidNames() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validatePrenom("Alice"));
        assertDoesNotThrow(() -> UserValidator.validatePrenom("Jean-Paul"));
        assertDoesNotThrow(() -> UserValidator.validatePrenom("María Elena"));
    }

    @Test
    void validatePrenom_InvalidNames() {
        assertThrows(NameFormatException.class, () -> UserValidator.validatePrenom("Alice123"));
        assertThrows(NameFormatException.class, () -> UserValidator.validatePrenom(""));
        assertThrows(NameFormatException.class, () -> UserValidator.validatePrenom("P@ul"));
    }
}
