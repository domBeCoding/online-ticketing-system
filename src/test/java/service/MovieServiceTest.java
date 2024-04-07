package service;

import org.example.exception.NoSuchFieldException;
import org.example.exception.NoSuchMovieException;
import org.example.model.Booking;
import org.example.model.Movie;
import org.example.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieServiceTest {
    private final MovieService movieService = new MovieService();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private final List<Movie> movies = Arrays.asList(
            new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00),
            new Movie("Movie 2", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00),
            new Movie("Movie 3", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00)
    );

    @BeforeEach
    public void setup() {
        movieService.eraseAllMovies();
        movieService.addMovies(movies);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void deleteAllFilmsSuccessfully() {
        movieService.eraseAllMovies();
        assertEquals(Collections.emptyList(), movieService.getMovies());
    }

    @Test
    public void deleteSingleFilmSuccessfully() {
        List<Movie> expectedMovies = Arrays.asList(
                new Movie("Movie 2", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00),
                new Movie("Movie 3", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00)
        );

        movieService.eraseMovieByName("Movie 1");
        assertEquals(expectedMovies, movieService.getMovies());
    }

    @Test
    public void deleteMultipleFilmsSuccessfully() {
        List<Movie> expectedMovies = Arrays.asList(
                new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00)
        );

        movieService.eraseMovieByName("Movie 2");
        movieService.eraseMovieByName("Movie 3");
        assertEquals(expectedMovies, movieService.getMovies());
    }

    @Test
    public void throwsExceptionsOnSingleDeletion_whenFilmNotPresent() {
        String exceptionMessage = assertThrows(
                RuntimeException.class,
                () -> movieService.eraseMovieByName("Movie 99")).getMessage();

        assertEquals("Movie not found: Movie 99", exceptionMessage);
        assertEquals(movies, movieService.getMovies());
    }

    @Test
    public void throwsExceptionsOnMultiDeletion_forFirstFilmNotPresent() {
        List<Movie> moviesToBeRemoved = Arrays.asList(
                new Movie("Movie 99", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00),
                new Movie("Movie 100", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00)
        );

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> movieService.eraseMovies(moviesToBeRemoved));

        assertEquals(
                "Movie not found: Movie 99",
                exception.getMessage());
        assertEquals(movies, movieService.getMovies());
    }

    @Test
    public void addFilmsSuccessfully() {
        movieService.eraseAllMovies();

        List<Movie> movies = Arrays.asList(
                new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00),
                new Movie("Movie 2", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00),
                new Movie("Movie 3", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00)
        );

        movieService.addMovies(movies);
        List<Movie> moviesInDb = movieService.getMovies();

        assertEquals(movies, moviesInDb);
    }

    @Test
    public void throwsWhenAddFilmsAllExists() {
        List<Movie> moviesToAdd = Arrays.asList(
                new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00),
                new Movie("Movie 2", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00)
        );

        String exceptionMessage = assertThrows(RuntimeException.class,
                () -> movieService.addMovies(moviesToAdd)).getMessage();
        assertEquals(movies, movieService.getMovies());
        assertEquals(
                "Added elements all already exist in DB: " +
                "[Movie{name='Movie 1', length=60, rating=5, price=10.0}, " +
                "Movie{name='Movie 2', length=60, rating=5, price=10.0}]",
                exceptionMessage);
    }

    @Test
    public void addFilmSuccessWhenOneFilmExistsAndAnotherOneDoesNot() {
        List<Movie> expectedMovies = Arrays.asList(
                new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00),
                new Movie("Movie 2", List.of("13:00", "20:00"), List.of("10-01-25", "11-01-25"), 60, 5, 10.00),
                new Movie("Movie 3", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00),
                new Movie("Movie 10", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 120, 3, 10.00)
        );

        List<Movie> moviesToAdd = Arrays.asList(
                new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00),
                new Movie("Movie 10", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 120, 3, 10.00)
        );

        assertDoesNotThrow(() -> movieService.addMovies(moviesToAdd));
        assertEquals(expectedMovies, movieService.getMovies());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Movie 1, price, 15.00",
            "Movie 2, length, 120",
            "Movie 3, rating, 4"
    })
    public void editFilmNameSuccessfully(String movie, String variable, String value) {

        assertDoesNotThrow(() -> movieService.editMovie(movie, variable, value));
        assertDoesNotThrow(() -> movieService.getMovieByName(movie));
        assertNotEquals(movies, movieService.getMovies());
        assertNotEquals("Movie not found", outputStreamCaptor.toString().trim());
    }

    @Test
    public void editFilmNameSuccessful() {
        Movie expectMovie = new Movie("Harry Potter", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00);

        assertDoesNotThrow(() -> movieService.editMovie("Movie 3", "name", "Harry Potter"));
        assertEquals(expectMovie, movieService.getMovieByName("Harry Potter"));
        assertThrows(NoSuchMovieException.class, () -> movieService.getMovieByName("Movie 3"));
    }

    @Test
    public void editInvalidFilmNameThrowsException() {
        String exceptionMessage = assertThrows(NoSuchFieldException.class,
                () -> movieService.editMovie("Non-existent movie", "name", "Harry Potter")).getMessage();
        assertEquals("Movie not found: Non-existent movie", exceptionMessage);
    }

    @Test
    public void editInvalidFieldThrowsException() {
        String exceptionMessage = assertThrows(NoSuchFieldException.class,
                () -> movieService.editMovie("Movie 1", "invalidField", "n/a")).getMessage();
        assertEquals("Field not valid: invalidField", exceptionMessage);
    }

    @Test
    public void editFieldPrioritiseInvalidMovieOverInvalidProperty() {
        String exceptionMessage = assertThrows(NoSuchFieldException.class,
                () -> movieService.editMovie("Non-existent movie", "invalidField", "n/a")).getMessage();
        assertEquals("Movie not found: Non-existent movie", exceptionMessage);
    }

    @Test
    public void getSingleFilmByNameSuccessfully() {
        Movie film = assertDoesNotThrow(() -> movieService.getMovieByName("Movie 1"));
        assertEquals(new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00), film);
    }

    @Test
    public void throwsWhenGetFilmByNameNotExist() {
        assertThrows(NoSuchMovieException.class, () -> movieService.getMovieByName("Non-existent movie"));
    }

    @Test
    public void whenAddBookingToMovie_thenBookingIsAddedToDb() {
        movieService.addBooking("Movie 1", new Booking("12:00", "05-01-25", List.of("2A", "2B")));

        List<Booking> actualBooking = movieService.getMovieByName("Movie 1").getBookings().stream().toList();
        List<Booking> expectedBooking = List.of(new Booking("12:00", "05-01-25", List.of("2A", "2B")));

        assertEquals(expectedBooking, actualBooking);
    }

    @Test
    public void doesNotThrow_whenMovieExistForDateAndTime() {
        Movie movie = new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00);
        movie.addBooking(new Booking("05-01-25", "12:00", List.of("2A", "2B")));

        assertDoesNotThrow(() -> movieService.checkMovieExistsForDateAndTime(movie, "05-01-25", "12:00"));
    }

    @Test
    public void throwsException_whenMovieDoesNotExistForDate() {
        Movie movie = new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00);
        movie.addBooking(new Booking("05-01-25", "12:00", List.of("2A", "2B")));

        String exceptionMessage = assertThrows(NoSuchMovieException.class, () -> movieService.checkMovieExistsForDateAndTime(movie, "10-01-25", "12:00")).getMessage();
        assertEquals("Movie 1 has no showing on: 10-01-25 at 12:00", exceptionMessage);
    }

    @Test
    public void throwsException_whenMovieDoesNotExistForTime() {
        Movie movie = new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00);
        movie.addBooking(new Booking("05-01-25", "12:00", List.of("2A", "2B")));

        String exceptionMessage = assertThrows(NoSuchMovieException.class, () -> movieService.checkMovieExistsForDateAndTime(movie, "05-01-25", "20:00")).getMessage();
        assertEquals("Movie 1 has no showing on: 05-01-25 at 20:00", exceptionMessage);
    }

    @Test
    public void throwsException_whenMovieDoesNotExistForDateAndTime() {
        Movie movie = new Movie("Movie 1", List.of("12:00", "19:00"), List.of("05-01-25", "06-01-25"), 60, 5, 10.00);
        movie.addBooking(new Booking("05-01-25", "12:00", List.of("2A", "2B")));

        String exceptionMessage = assertThrows(NoSuchMovieException.class, () -> movieService.checkMovieExistsForDateAndTime(movie, "25-01-25", "20:00")).getMessage();
        assertEquals("Movie 1 has no showing on: 25-01-25 at 20:00", exceptionMessage);
    }
}
