package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {
    @Test
    void validateMonthOfBirth_ValidMonths() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateMonthOfBirth(1));
        assertDoesNotThrow(() -> UserValidator.validateMonthOfBirth(6));
        assertDoesNotThrow(() -> UserValidator.validateMonthOfBirth(12));
    }

    @Test
    void validateMonthOfBirth_InvalidMonths() {
        assertThrows(DateFormatException.class, () -> UserValidator.validateMonthOfBirth(0));
        assertThrows(DateFormatException.class, () -> UserValidator.validateMonthOfBirth(13));
        assertThrows(DateFormatException.class, () -> UserValidator.validateMonthOfBirth(-1));
    }

    @Test
    void validateDayOfBirth_ValidDays() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateDayOfBirth(1));
        assertDoesNotThrow(() -> UserValidator.validateDayOfBirth(15));
        assertDoesNotThrow(() -> UserValidator.validateDayOfBirth(31));
    }

    @Test
    void validateDayOfBirth_InvalidDays() {
        assertThrows(DateFormatException.class, () -> UserValidator.validateDayOfBirth(0));
        assertThrows(DateFormatException.class, () -> UserValidator.validateDayOfBirth(32));
        assertThrows(DateFormatException.class, () -> UserValidator.validateDayOfBirth(-1));
    }

    @Test
    void validateYearOfBirth_ValidYears() throws RegexValidationException {
        assertDoesNotThrow(() -> UserValidator.validateYearOfBirth(1900));
        assertDoesNotThrow(() -> UserValidator.validateYearOfBirth(1990));
        assertDoesNotThrow(() -> UserValidator.validateYearOfBirth(2023));
    }

    @Test
    void validateYearOfBirth_InvalidYears() {
        assertThrows(DateFormatException.class, () -> UserValidator.validateYearOfBirth(1899)); // Too early
        assertThrows(DateFormatException.class, () -> UserValidator.validateYearOfBirth(2025)); // Future year
        assertThrows(DateFormatException.class, () -> UserValidator.validateYearOfBirth(-1));
    }
}
