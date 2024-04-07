package validator;

import org.example.exception.*;
import org.example.validator.MovieValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieValidatorTest {

    @Test
    public void doesNotThrow_whenAllFieldsValid() {
        assertDoesNotThrow(() -> MovieValidator.validateFields(
                "Dune",
                List.of("20:00", "21:00", "22:00"),
                "20-10-24",
                "10",
                "120",
                "5",
                "10.00"));
    }

    @Test
    public void throwException_whenMovieNameEmpty() {
        String exceptionMessage = assertThrows(InvalidStringException.class, () -> MovieValidator.validateFields(
                "",
                List.of("20:00"),
                "20-02-24",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Movie field invalid - cannot be empty", exceptionMessage);
    }

    @Test
    public void throwException_whenTimeFieldEmpty() {
        String exceptionMessage = assertThrows(InvalidTimeException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of(""),
                "20-02-24",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Time field invalid", exceptionMessage);
    }

    @Test
    public void throwException_whenTimeFieldIncorrectFormat() {
        String exceptionMessage = assertThrows(InvalidTimeException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("incorrect_format"),
                "20-02-24",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Time field invalid", exceptionMessage);
    }

    @Test
    public void throwException_whenTimeFieldGreaterThan24h() {
        String exceptionMessage = assertThrows(InvalidTimeException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("25:00"),
                "20-02-24",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Time field invalid", exceptionMessage);
    }

    @Test
    public void throwException_whenDateFieldEmpty() {
        String exceptionMessage = assertThrows(InvalidDateException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Date invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_whenDateFieldIncorrectFormat() {
        String exceptionMessage = assertThrows(InvalidDateException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "Incorrect_format",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Date invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_whenDateFieldGreaterThanMaxDay() {
        String exceptionMessage = assertThrows(InvalidDateException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "40-01-24",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Date invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_whenDateFieldGreaterThanMaxMonth() {
        String exceptionMessage = assertThrows(InvalidDateException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "01-20-24",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Date invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_whenDateFieldInThePast() {
        String exceptionMessage = assertThrows(InvalidDateException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "01-11-19",
                "10",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Date invalid - date input is in the past", exceptionMessage);
    }

    @Test
    public void throwException_whenNumberFieldEmpty() {
        String exceptionMessage = assertThrows(InvalidNumberException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "20-12-24",
                "",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Number field invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_whenNumberFieldIsZero() {
        String exceptionMessage = assertThrows(InvalidNumberException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("12:00"),
                "20-10-24",
                "0",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Number field invalid - less than zero or zero", exceptionMessage);
    }

    @Test
    public void throwException_whenNumberFieldPassString() {
        String exceptionMessage = assertThrows(InvalidNumberException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("23:00"),
                "20-10-24",
                "StringInput",
                "120",
                "5",
                "10.00")).getMessage();

        assertEquals("Number field invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_RatingFieldPassString() {
        String exceptionMessage = assertThrows(InvalidRatingException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("23:00"),
                "20-10-24",
                "10",
                "120",
                "StringInput",
                "10.00")).getMessage();

        assertEquals("Rating field invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_RatingFieldGreaterThan5() {
        String exceptionMessage = assertThrows(InvalidRatingException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("23:00"),
                "20-10-24",
                "10",
                "120",
                "6",
                "10.00")).getMessage();

        assertEquals("Rating field invalid - greater than 5", exceptionMessage);
    }

    @Test
    public void throwException_PriceFieldPassString() {
        String exceptionMessage = assertThrows(InvalidPriceException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("23:00"),
                "20-10-24",
                "10",
                "120",
                "5",
                "StringInput")).getMessage();

        assertEquals("Price field invalid - incorrect format", exceptionMessage);
    }

    @Test
    public void throwException_PriceFieldIsZero() {
        String exceptionMessage = assertThrows(InvalidPriceException.class, () -> MovieValidator.validateFields(
                "Dune 3",
                List.of("23:00"),
                "20-10-24",
                "10",
                "120",
                "5",
                "0.0")).getMessage();

        assertEquals("Price field invalid - cannot be zero", exceptionMessage);
    }
}
