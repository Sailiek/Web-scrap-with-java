package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTypeValidatorTest {
    @Test
    void validateUserType_ValidTypes() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateUserType(UserTypes.CLIENT));
        assertDoesNotThrow(() -> UserValidator.validateUserType(UserTypes.ADMIN));
    }

    @Test
    void validateUserType_InvalidTypes() {
        assertThrows(UserTypeFormatException.class, () -> UserValidator.validateUserType(null));
    }
}
