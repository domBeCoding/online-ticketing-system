package org.example;

import org.example.model.Booking;
import org.example.model.Customer;
import org.example.model.Movie;
import org.example.service.ManagementService;
import org.example.service.MovieService;

import java.util.Arrays;
import java.util.List;

public class PrePopulationJob {


    public static void main(String[] args) {

        List<Movie> movies = Arrays.asList(
                new Movie("Ghostbusters", List.of("14:00", "21:00"), List.of("15-01-25", "16-01-25"), 60, 5, 10.00),
                new Movie("Dune", List.of("15:00", "20:00"), List.of("20-01-25", "21-01-25"), 60, 5, 10.00),
                new Movie("Civil War", List.of("16:00", "23:00"), List.of("25-01-25", "26-01-25"), 60, 5, 10.00)
        );

        List<Customer> customers = List.of(
                new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234),
                new Customer("Zoe", "zoe@email.com", "Zoe_user", "password", 942483, 9876)
        );

        // Pre-populate the database with movies and bookings
        MovieService movieService = new MovieService();
        movieService.eraseAllMovies();
        movieService.addMovies(movies);
        movieService.addBooking("Ghostbusters", new Booking("15-01-25", "14:00", List.of("1A", "5B", "4D")));
        movieService.addBooking("Ghostbusters", new Booking("15-01-25", "21:00", List.of("4B", "2C", "3B")));
        movieService.addBooking("Ghostbusters", new Booking("16-01-25", "14:00", List.of("2A", "4B", "3D")));
        movieService.addBooking("Ghostbusters", new Booking("16-01-25", "21:00", List.of("5B", "1C", "4B")));

        movieService.addBooking("Dune", new Booking("20-01-25", "15:00", List.of("1A", "5B", "4D")));
        movieService.addBooking("Dune", new Booking("20-01-25", "20:00", List.of("4B", "2C", "3B")));
        movieService.addBooking("Dune", new Booking("21-01-25", "15:00", List.of("2A", "4B", "3D")));
        movieService.addBooking("Dune", new Booking("21-01-25", "20:00", List.of("5B", "3C", "4B")));

        movieService.addBooking("Civil War", new Booking("25-01-25", "16:00", List.of("1A", "5B", "4D")));
        movieService.addBooking("Civil War", new Booking("25-01-25", "23:00", List.of("4B", "2C", "3B")));
        movieService.addBooking("Civil War", new Booking("26-01-25", "16:00", List.of("2A", "4B", "3D")));
        movieService.addBooking("Civil War", new Booking("26-01-25", "23:00", List.of("5B", "3C", "4B")));

        // Reset Customer tickets
        ManagementService managementService = new ManagementService();
        managementService.removeAll();
        managementService.addCustomers(customers);
    }
}
