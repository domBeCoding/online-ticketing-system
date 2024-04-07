package org.example.validator;

import java.util.List;

import static org.example.validator.Validator.*;

public class MovieValidator {
    public static boolean validateFields(String movieName, List<String> availableTimes, String startDate, String airingDays, String length, String rating, String price) {
        return validateStringField(movieName)
                && validateTimes(availableTimes)
                && validateDate(startDate)
                && validateNumber(airingDays)
                && validateNumber(length)
                && validateRating(rating)
                && validatePrice(price);
    }
}
