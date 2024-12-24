package data.model;

import data.model.RegexExceptions.*;
import java.util.regex.Pattern;

public class UserValidator {
    
    public static void validateNom(String nom) throws RegexValidationException {
        if (!Pattern.matches(UserRegularExpressions.NOM_REGEX, nom)) {
            throw new NameFormatException("Name must contain only letters, hyphens, or spaces (1-100 characters)");
        }
    }

    public static void validatePrenom(String prenom) throws RegexValidationException {
        if (!Pattern.matches(UserRegularExpressions.PRENOM_REGEX, prenom)) {
            throw new NameFormatException("First name must contain only letters, hyphens, or spaces (1-100 characters)");
        }
    }

    public static void validateUserEmail(String email) throws RegexValidationException {
        if (!Pattern.matches(UserRegularExpressions.USER_EMAIL_REGEX, email)) {
            throw new EmailFormatException("Invalid email format");
        }
    }

    public static void validateUsername(String username) throws RegexValidationException {
        if (!Pattern.matches(UserRegularExpressions.USERNAME_REGEX, username)) {
            throw new UsernameFormatException("Username must be alphanumeric with underscores only (3-100 characters)");
        }
    }

    public static void validateUserPassword(String password) throws RegexValidationException {
        if (!Pattern.matches(UserRegularExpressions.USER_PASSWORD_REGEX, password)) {
            throw new PasswordFormatException("Password must contain at least 8 characters, including 1 uppercase, 1 lowercase, 1 digit, and 1 special character");
        }
    }

    public static void validateFieldOfWork(String fieldOfWork) throws RegexValidationException {
        if (!Pattern.matches(UserRegularExpressions.FIELD_OF_WORK_REGEX, fieldOfWork)) {
            throw new FieldOfWorkFormatException("Field of work must contain only letters, hyphens, or spaces (1-100 characters)");
        }
    }

    public static void validateAge(int age) throws RegexValidationException {
        if (age < 15 || age > 100) {
            throw new AgeFormatException("Age format error: Age must be between 15 and 100");
        }
    }

    public static void validateUserType(UserTypes userType) throws RegexValidationException {
        if (userType == null || !Pattern.matches(UserRegularExpressions.USER_TYPE_REGEX, userType.toString())) {
            throw new UserTypeFormatException("Invalid user type. Must be one of: GUEST, CLIENT, ADMIN, SUPERADMIN");
        }
    }

    public static void validateMonthOfBirth(int month) throws RegexValidationException {
        if (month < 1 || month > 12) {
            throw new DateFormatException("Month must be between 1 and 12");
        }
    }

    public static void validateDayOfBirth(int day) throws RegexValidationException {
        if (day < 1 || day > 31) {
            throw new DateFormatException("Day must be between 1 and 31");
        }
    }

    public static void validateYearOfBirth(int year) throws RegexValidationException {
        if (year < 1900 || year > 2023) {  // Using current year as max
            throw new DateFormatException("Year must be a 4-digit number between 1900 and 2023");
        }
    }

    // Validate all user fields at once
    public static void validateUser(User user) throws RegexValidationException {
        validateNom(user.getNom());
        validatePrenom(user.getPrenom());
        validateUserEmail(user.getUserEmail());
        validateUsername(user.getUsername());
        validateUserPassword(user.getUserPassword());
        validateFieldOfWork(user.getFieldOfWork());
        validateAge(user.getAge());
        validateUserType(user.getUserType());
        validateMonthOfBirth(user.getMonthOfBirth());
        validateDayOfBirth(user.getDayOfBirth());
        validateYearOfBirth(user.getYearOfBirth());
    }
}
