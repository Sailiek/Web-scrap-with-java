package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AgeValidatorTest {
    @Test
    void validateAge_ValidAges() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateAge(15));
        assertDoesNotThrow(() -> UserValidator.validateAge(50));
        assertDoesNotThrow(() -> UserValidator.validateAge(100));
    }

    @Test
    void validateAge_InvalidAges() {
        assertThrows(AgeFormatException.class, () -> UserValidator.validateAge(14)); // Too young
        assertThrows(AgeFormatException.class, () -> UserValidator.validateAge(101)); // Too old
        assertThrows(AgeFormatException.class, () -> UserValidator.validateAge(-1)); // Negative age
    }
}
