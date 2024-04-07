package org.example.validator;

public class EditMovieValidator extends Validator{

    public static boolean validate(String movieName, String field, String value) {
        return validateStringField(movieName)
                && validateStringField(field)
                && validateStringField(value);
    }

}
