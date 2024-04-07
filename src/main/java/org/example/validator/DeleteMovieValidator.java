package org.example.validator;

public class DeleteMovieValidator extends Validator{

    public static boolean validate(String movieName) {
        return validateStringField(movieName);
    }
}


