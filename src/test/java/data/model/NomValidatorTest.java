package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NomValidatorTest {
    @Test
    void validateNom_ValidNames() throws RegexValidationException {
        // Test valid names
        assertDoesNotThrow(() -> UserValidator.validateNom("John"));
        assertDoesNotThrow(() -> UserValidator.validateNom("Jean-Pierre"));
        assertDoesNotThrow(() -> UserValidator.validateNom("María José"));
    }

    @Test
    void validateNom_InvalidNames() {
        // Test invalid names
        assertThrows(NameFormatException.class, () -> UserValidator.validateNom("John123"));
        assertThrows(NameFormatException.class, () -> UserValidator.validateNom(""));
        assertThrows(NameFormatException.class, () -> UserValidator.validateNom("J@mes"));
    }
}
