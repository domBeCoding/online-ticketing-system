package service;

import org.example.exception.NoSuchSeatException;
import org.example.model.Booking;
import org.example.model.Movie;
import org.example.service.CinemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaServiceTest {
    CinemaService cinemaService = new CinemaService(5, 10);
    Movie movie;

    private final List<Movie> movies = Arrays.asList(
            new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00),
            new Movie("Movie 2", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00),
            new Movie("Movie 3", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00)
    );

    @BeforeEach
    public void setup() {
        movie = new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00);
        movie.addBooking(new Booking("05-01-25", "12:00", List.of("2A", "2B")));
    }

    @Test
    public void doesNotThrowException_whenSeatAvailable() {
        Booking potentialBooking = new Booking("05-01-25", "12:00", List.of("2C", "2D"));
        assertDoesNotThrow(() -> cinemaService.checkSeatAvailability(movie, potentialBooking));
    }

    @Test
    public void throwsException_whenFirstSeatNotAvailable() {
        Booking potentialBooking = new Booking("05-01-25", "12:00", List.of("2A", "2D"));
        String exceptionMessage = assertThrows(NoSuchSeatException.class, () -> cinemaService.checkSeatAvailability(movie, potentialBooking)).getMessage();
        assertEquals("Seat 2A already booked", exceptionMessage);
    }

    @Test
    public void throwsException_whenSeatNotExist() {
        Booking potentialBooking = new Booking("05-01-25", "12:00", List.of("6A"));
        String exceptionMessage = assertThrows(NoSuchSeatException.class, () -> cinemaService.checkSeatAvailability(movie, potentialBooking)).getMessage();
        assertEquals("Seat 6A does not exist", exceptionMessage);
    }

    @Test
    public void populateSeatsSuccessfully() {
        CinemaService cinemaService = new CinemaService(5, 10);
        cinemaService.populateSeats(movie, "05-01-25", "12:00");

        assertThrows(IllegalArgumentException.class, () -> cinemaService.bookSeat("2A"));
        assertThrows(IllegalArgumentException.class, () -> cinemaService.bookSeat("2B"));
        assertDoesNotThrow(() -> cinemaService.bookSeat("2C"));
    }

    @Test
    public void bookSeatUnsuccessful_whenAlreadyBooked() {
        CinemaService cinemaService = new CinemaService(5, 10);

        cinemaService.bookSeat("2A");
        assertThrows(IllegalArgumentException.class, () -> cinemaService.bookSeat("2A"));
    }

    @Test
    public void bookSeatUnsuccessful_whenSeatOutOfBounds() {
        CinemaService cinemaService = new CinemaService(5, 10);
        assertThrows(IllegalArgumentException.class, () -> cinemaService.bookSeat("6A"));
    }
}
