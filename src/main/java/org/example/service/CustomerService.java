package org.example.service;

import org.example.model.*;
import org.example.reader.CustomerReader;
import org.example.reader.MoviesReader;
import org.example.writer.CustomerWriter;

import java.util.List;

public class CustomerService {

    private final Customer customer;
    private final CustomerWriter writer;
    private final CustomerReader reader;
    private final MoviesReader moviesReader;
    private final PaymentService paymentService;

    public CustomerService(Customer customer) {
        this.customer = customer;
        writer = new CustomerWriter();
        reader = new CustomerReader();
        moviesReader = new MoviesReader();
        paymentService = new PaymentService();
    }

    public void buyTicket(String movieName, Booking newBooking, Transaction transaction) {
        Movie movie = getMovie(movieName);
        paymentService.pay(customer, transaction);

        Ticket ticket = createTicket(
                movie,
                newBooking.getTime(),
                newBooking.getDate(),
                newBooking.getSeats());

        writer.addTicket(customer.getUserId(), ticket);
    }

    public void addTicket(String movieName, Booking newBooking) {
        Movie movie = getMovie(movieName);

        Ticket ticket = createTicket(
                movie,
                newBooking.getTime(),
                newBooking.getDate(),
                newBooking.getSeats());

        writer.addTicket(customer.getUserId(), ticket);
    }

    public List<Ticket> getTickets(String userId) {
        return reader.getTickets(userId);
    }

    private Movie getMovie(String movie) {
        return moviesReader.get(movie);
    }

    private List<Movie> getAllMovies() {
        return moviesReader.getAll();
    }

    private Ticket createTicket(Movie movie, String time, String date, List<String> seats) {
        return new Ticket(movie.getName(), time, date, seats, movie.getPrice());
    }


}
