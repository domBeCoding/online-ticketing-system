package validator;

import org.example.exception.NoSuchSeatException;
import org.example.validator.SeatValidator;
import org.junit.jupiter.api.Test;

import static org.example.validator.SeatValidator.validate;
import static org.junit.jupiter.api.Assertions.*;

public class SeatValidatorTest {

    SeatValidator seatValidator = new SeatValidator();

    @Test
    public void doesNotThrow_whenSeatValidFormat(){
        assertDoesNotThrow(() -> validate("2A"));
    }

    @Test
    public void throwsException_whenTwoDigitSeat(){
        String exceptionMessage = assertThrows(NoSuchSeatException.class,
                () -> validate("10A")).getMessage();
        assertEquals("Seat invalid, please follow the format of 1A, 2B, 3C, etc. " +
                        "With no bigger than one digit number",
                exceptionMessage);
    }

    @Test
    public void throwsException_whenNoLetter(){
        String exceptionMessage = assertThrows(NoSuchSeatException.class,
                () -> validate("1")).getMessage();
        assertEquals("Seat invalid, please follow the format of 1A, 2B, 3C, etc." +
                        " With no bigger than one digit number",
                exceptionMessage);
    }

    @Test
    public void throwsException_whenNoNumber(){
        String exceptionMessage = assertThrows(NoSuchSeatException.class,
                () -> validate("A")).getMessage();
        assertEquals("Seat invalid, please follow the format of 1A, 2B, 3C, etc." +
                        " With no bigger than one digit number",
                exceptionMessage);
    }
}
