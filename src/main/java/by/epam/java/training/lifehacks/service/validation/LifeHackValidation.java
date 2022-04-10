package by.epam.java.training.lifehacks.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifeHackValidation {
    private static Pattern namePattern = Pattern.compile("^[A-Z][a-z,'\\- ]{7,40}$");
    private static Pattern descriptionPattern = Pattern.compile("[A-Za-z$&+,:;=?@#|'<>.^*()%!/\"\\-\\s]{10,200}$");

    private LifeHackValidation() {
    }


    public static boolean validateName(String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validateDescription(String description) {
        Matcher matcher = descriptionPattern.matcher(description);
        return matcher.matches();
    }


}
