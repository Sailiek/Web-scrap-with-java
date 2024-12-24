package data.model;

import data.model.RegexExceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @Test
    void validateUser_ValidUser() throws RegexValidationException {
        User validUser = new User();
        validUser.setNom("John");
        validUser.setPrenom("Doe");
        validUser.setUserEmail("john.doe@example.com");
        validUser.setUsername("john_doe");
        validUser.setUserPassword("Password1!");
        validUser.setFieldOfWork("Software Engineering");
        validUser.setAge(30);
        validUser.setUserType(UserTypes.CLIENT);
        validUser.setDayOfBirth(15);
        validUser.setMonthOfBirth(6);
        validUser.setYearOfBirth(1993);

        assertDoesNotThrow(() -> UserValidator.validateUser(validUser));
    }

    @Test
    void validateUser_InvalidUser() {
        User invalidUser = new User();
        invalidUser.setNom("John123"); // Invalid name
        invalidUser.setPrenom("Doe");
        invalidUser.setUserEmail("invalid.email"); // Invalid email
        invalidUser.setUsername("jd"); // Too short
        invalidUser.setUserPassword("weak"); // Invalid password
        invalidUser.setFieldOfWork("IT123"); // Invalid field
        invalidUser.setAge(14); // Invalid age
        invalidUser.setUserType(UserTypes.CLIENT);
        invalidUser.setDayOfBirth(32); // Invalid day
        invalidUser.setMonthOfBirth(13); // Invalid month
        invalidUser.setYearOfBirth(1899); // Invalid year

        assertThrows(RegexValidationException.class, () -> UserValidator.validateUser(invalidUser));
    }
}
