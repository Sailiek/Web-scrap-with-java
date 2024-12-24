package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FieldOfWorkValidatorTest {
    @Test
    void validateFieldOfWork_ValidFields() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateFieldOfWork("Software Engineering"));
        assertDoesNotThrow(() -> UserValidator.validateFieldOfWork("Data Science"));
        assertDoesNotThrow(() -> UserValidator.validateFieldOfWork("Human Resources"));
    }

    @Test
    void validateFieldOfWork_InvalidFields() {
        assertThrows(FieldOfWorkFormatException.class, () -> UserValidator.validateFieldOfWork("Software123"));
        assertThrows(FieldOfWorkFormatException.class, () -> UserValidator.validateFieldOfWork(""));
        assertThrows(FieldOfWorkFormatException.class, () -> UserValidator.validateFieldOfWork("Field@Work"));
    }
}
