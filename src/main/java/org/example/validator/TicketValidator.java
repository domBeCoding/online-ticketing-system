package org.example.validator;

import org.example.exception.InvalidTimeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketValidator extends Validator {
    public static boolean validateFields(String movieName, String time, String date) {
        return validateStringField(movieName)
                && validateTime(time)
                && validateDate(date);
    }

    private static boolean validateTime(String time) {
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            throw new InvalidTimeException("Time field invalid");
        }
        return true;
    }
}
