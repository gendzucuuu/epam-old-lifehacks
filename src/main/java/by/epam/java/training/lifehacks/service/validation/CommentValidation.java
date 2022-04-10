package by.epam.java.training.lifehacks.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentValidation {
    private static Pattern commentPattern = Pattern.compile("^\\s*$");

    private CommentValidation() {

    }

    public static boolean validateComment(String comment) {
        Matcher matcher = commentPattern.matcher(comment);
        return !matcher.matches();
    }
}
