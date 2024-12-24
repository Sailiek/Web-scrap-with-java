package data.model;

public final class UserRegularExpressions {

    // Regex for 'nom' and 'prenom' (letters, optional hyphens or spaces, 1-100 characters)
    public static final String NOM_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\-\\s]{1,100}$";
    public static final String PRENOM_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\-\\s]{1,100}$";

    // Regex for 'userEmail' (standard email format)
    public static final String USER_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Regex for 'username' (alphanumeric, underscores, 3-100 characters)
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,100}$";

    // Regex for 'userPassword' (min 8 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character)
    public static final String USER_PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";


    public static final String FIELD_OF_WORK_REGEX = "^[A-Za-z\\-\\s]{1,100}$";

    // Regex for 'age' (numeric, 0 to 150)
    public static final String AGE_REGEX = "^(1[5-9]|[2-9]\\d|100)$";

    // Regex for 'userType' (enum values: ADMIN, MODERATOR, BASIC, GUEST)
    public static final String USER_TYPE_REGEX = "^(GUEST|CLIENT|ADMIN|SUPERADMIN)$";

    // Regex for 'monthOfBirth' (1 to 12)
    public static final String MONTH_OF_BIRTH_REGEX = "^(0?[1-9]|1[0-2])$";

    // Regex for 'dayOfBirth' (1 to 31)
    public static final String DAY_OF_BIRTH_REGEX = "^(0?[1-9]|[12]\\d|3[01])$";

    // Regex for 'yearOfBirth' (4-digit year, between 1900 and current year)
    public static final String YEAR_OF_BIRTH_REGEX = "^(19[0-9]{2}|20[0-1][0-9]|202[0-3])$";



}
