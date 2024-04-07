package org.example.validator;

import org.example.exception.NoSuchSeatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeatValidator extends Validator{

    private static final String NUMBER_PATTERN = "\\d[A-Za-z]";

    public static boolean validate(String seatInput) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(seatInput);

        if (!matcher.matches()) {
            throw new NoSuchSeatException(
                    "Seat invalid, please follow the format of 1A, 2B, 3C, etc. " +
                    "With no bigger than one digit number");
        }
        return true;
    }
}
