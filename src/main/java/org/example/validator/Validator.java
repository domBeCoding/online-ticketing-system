package org.example.validator;

import org.example.exception.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    protected static final String TIME24HOURS_PATTERN = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

    protected static boolean validateTimes(List<String> times) {

        times.forEach(time -> {
            Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
            Matcher matcher = pattern.matcher(time);
            if (!matcher.matches()) {
                throw new InvalidTimeException("Time field invalid");
            }
        });

        return true;
    }

    protected static boolean validateDate(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

        try {
            LocalDate date = LocalDate.parse(inputDate, formatter);
            LocalDate currentDate = LocalDate.now();
            if (date.isBefore(currentDate)) {
                throw new InvalidDateException("Date invalid - date input is in the past");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Date invalid - incorrect format");
        }

        return true;
    }

    static boolean validateStringField(String string) {
        if (string == null || string.isEmpty()) {
            throw new InvalidStringException("Movie field invalid - cannot be empty");
        }
        return true;
    }

    protected static boolean validateNumber(String length) {
        try {
            int number = Integer.parseInt(length);
            if (number <= 0) throw new InvalidNumberException("Number field invalid - less than zero or zero");
        } catch (NumberFormatException e) {
            throw new InvalidNumberException("Number field invalid - incorrect format");
        }

        return true;
    }

    protected static boolean validateRating(String rating) {

        try {
            int number = Integer.parseInt(rating);
            if (number > 5) throw new InvalidRatingException("Rating field invalid - greater than 5");
        } catch (NumberFormatException e) {
            throw new InvalidRatingException("Rating field invalid - incorrect format");

        }
        return true;
    }

    protected static boolean validatePrice(String rating) {

        try {
            double number = Double.parseDouble(rating);
            if (number <= 0.0) throw new InvalidPriceException("Price field invalid - cannot be zero");
        } catch (NumberFormatException e) {
            throw new InvalidPriceException("Price field invalid - incorrect format");
        }
        return true;
    }
}
