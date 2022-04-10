package by.epam.java.training.lifehacks.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {
    private static Pattern usernamePattern = Pattern.compile("^[a-z0-9_-]{5,15}$");
    private static Pattern passwordPattern = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})");
    private static Pattern namePattern = Pattern.compile("[A-Z][a-z]{1,20}$");
    private static Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private UserValidation() {

    }
    public static boolean validateUsername(String username) {
        username = username.toLowerCase();

        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validateName(String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }


}
