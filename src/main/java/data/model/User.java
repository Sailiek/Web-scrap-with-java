package data.model;

public class User {
    private String nom;
    private String prenom;
    private String userEmail;
    private String username;
    private String userPassword;
    private String fieldOfWork;
    private int age;
    private UserTypes userType;
    private int monthOfBirth;
    private int dayOfBirth;
    private int yearOfBirth;

    // Constructor with parameters for all attributes
    public User(String nom, String prenom, String userEmail, String username, String userPassword, String fieldOfWork, int age, UserTypes userType, int monthOfBirth, int dayOfBirth, int yearOfBirth) {
        this.nom = nom;
        this.prenom = prenom;
        this.userEmail = userEmail;
        this.username = username;
        this.userPassword = userPassword;
        this.fieldOfWork = fieldOfWork;
        this.age = age;
        this.userType = userType;
        this.monthOfBirth = monthOfBirth;
        this.dayOfBirth = dayOfBirth;
        this.yearOfBirth = yearOfBirth;
    }

    // Copy constructor
    public User(User user) {
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.userEmail = user.getUserEmail();
        this.username = user.getUsername();
        this.userPassword = user.getUserPassword();
        this.fieldOfWork = user.getFieldOfWork();
        this.age = user.getAge();
        this.userType = user.getUserType();
        this.monthOfBirth = user.getMonthOfBirth();
        this.dayOfBirth = user.getDayOfBirth();
        this.yearOfBirth = user.getYearOfBirth();
    }

    // Default constructor
    public User() {
        this.nom = "Doe";
       this.prenom = "Joe";
        this.userEmail = "JoeDoe@example.com";
        this.username = "JoeDoe1";
        this.userPassword = "Pass123";
        this.fieldOfWork = "Engineering";
        this.age = 30;
        this.userType = UserTypes.CLIENT;
        this.monthOfBirth = 1;
        this.dayOfBirth = 1;
        this.yearOfBirth = 1990;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFieldOfWork() {
        return fieldOfWork;
    }

    public void setFieldOfWork(String fieldOfWork) {
        this.fieldOfWork = fieldOfWork;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(int dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}



