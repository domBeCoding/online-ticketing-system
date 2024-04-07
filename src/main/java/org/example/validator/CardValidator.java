package org.example.validator;

import org.example.exception.InvalidCardNumberException;
import org.example.exception.InvalidPinNumberException;
import org.example.exception.NoSuchSeatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator extends Validator{

    private static final String NUMBER_PATTERN = "\\d[A-Za-z]";

    public static boolean validate(String cardNumber, String pin) {
        return validateCardNumber(cardNumber) &&
                validatePin(pin);
    }

    private static boolean validateCardNumber(String cardNumber) {
        try{
            Integer.parseInt(cardNumber);
            if(cardNumber.length() > 6) throw new InvalidCardNumberException(
                    "Card number invalid, less than 6 digits");
        } catch (NumberFormatException e) {
            throw new InvalidCardNumberException(
                    "Card number invalid, please enter a 6 digit number");
        }
        return true;
    }

    private static boolean validatePin(String pin) {
        try{
            Integer.parseInt(pin);
            if(pin.length() > 4) throw new InvalidPinNumberException(
                    "Card number invalid, less than 6 digits");
        } catch (NumberFormatException e) {
            throw new InvalidPinNumberException(
                    "Pin number invalid, please enter a 6 digit number");
        }
        return true;
    }
}
