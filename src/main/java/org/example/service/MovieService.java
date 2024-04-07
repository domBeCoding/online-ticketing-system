package org.example.service;

import org.example.deleter.MoviesDeleter;
import org.example.exception.NoSuchMovieException;
import org.example.model.Booking;
import org.example.model.Movie;
import org.example.reader.MoviesReader;
import org.example.writer.MoviesWriter;

import java.util.Collections;
import java.util.List;

public class MovieService {

    private final MoviesWriter writer;
    private final MoviesReader reader;
    private final MoviesDeleter deleter;

    private Movie movie;

    public MovieService() {
        this.writer = new MoviesWriter();
        this.reader = new MoviesReader();
        this.deleter = new MoviesDeleter();
    }

    public List<Movie> getMovies() {
        return reader.getAll();
    }

    public Movie getMovieByName(String movieName) {
        return reader.get(movieName);
    }

    public void addMovies(List<Movie> movies) {
        writer.append(movies);
    }

    public void editMovie(String movieName, String field, String value) {
        writer.edit(movieName, field, value);
    }

    public void eraseAllMovies() {
        writer.overwrite(Collections.emptyList());
    }

    public void eraseMovies(List<Movie> movies) {
        deleter.remove(movies);
    }

    public void eraseMovieByName(String movieName) {
        deleter.removeByName(movieName);
    }

    public void addBooking(String movieName, Booking newBooking) {
        Movie movie = getMovieByName(movieName);
        List<Booking> bookings = movie.getBookings();

        boolean bookingExists = false;

        for (Booking booking : bookings) {
            if (booking.getDate().equals(newBooking.getDate()) && booking.getTime().equals(newBooking.getTime())) {
                booking.getSeats().addAll(newBooking.getSeats());
                bookingExists = true;
                break;
            }
        }

        if (!bookingExists) {
            bookings.add(newBooking);
        }

        eraseMovieByName(movieName);
        addMovies(Collections.singletonList(movie));
    }

    public void checkMovieExistsForDateAndTime(Movie movie, String date, String time) {
        boolean timeExists = movie.getAvailableTimes()
                .stream()
                .anyMatch(movieTime -> movieTime.equals(time));

        boolean dateExists = movie.getAvailableDates()
                .stream()
                .anyMatch(movieDate -> movieDate.equals(date));

        if (!timeExists || !dateExists) {
            throw new NoSuchMovieException(movie.getName() + " has no showing on: " + date + " at " + time);
        }
    }

}
