package service;

import org.example.exception.*;
import org.example.model.*;
import org.example.service.CustomerService;
import org.example.service.ManagementService;
import org.example.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {
    private CustomerService customerService;
    private final ManagementService managementService = new ManagementService();
    private final MovieService movieService = new MovieService();

    private final List<Customer> customers = List.of(
            new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234),
            new Customer("Zoe", "zoe@email.com", "Zoe_user", "password", 942483, 9876)
    );

    private final List<Movie> movies = List.of(
            new Movie("The Matrix", List.of("12:00", "19:00"), List.of("05-01-2024", "06-01-2024"), 120, 5, 15.00)
    );

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234));

        managementService.removeAll();
        movieService.eraseAllMovies();

        managementService.addCustomers(customers);
        movieService.addMovies(movies);
    }

    @Test
    public void buyTicketForExistingUserIdSuccessful() {

        Booking booking = new Booking("05-01-97", "12:00", List.of("2A", "2B"));
        Transaction transaction = new Transaction(30.00, "343242", "1234");

        assertDoesNotThrow(() -> customerService.buyTicket(
                "The Matrix",
                booking,
                transaction));

        Ticket expectedTicket = new Ticket("The Matrix", "12:00", "05-01-97", List.of("2A", "2B"), 15.00);

        List<Ticket> tickets = customerService.getTickets("Dominic_user");
        assertEquals(List.of(expectedTicket), tickets);
    }

    @Test
    public void buyTicketForNonExistingUserIdSuccessful() {
        customerService = new CustomerService(new Customer("Dominic", "dom@email.com", "non_existent_user", "password", 343242, 1234));

        Booking booking = new Booking("05-01-97", "12:00", List.of("2A", "2B"));
        Transaction transaction = new Transaction(30.00, "343242", "1234");

        String exceptionMessage = assertThrows(NoSuchAccountException.class, () -> customerService.buyTicket(
                "The Matrix",
                booking,
                transaction)).getMessage();

        assertEquals("Customer not found for userId: non_existent_user", exceptionMessage);
    }

    @Test
    public void throwsException_whenCardNumberIncorrectForExistingUser() {

        Booking booking = new Booking("05-01-97", "12:00", List.of("2A", "2B"));
        Transaction transaction = new Transaction(30.00, "123456", "1234");

        String exceptionMessage = assertThrows(InvalidCardNumberException.class, () -> customerService.buyTicket(
                "The Matrix",
                booking,
                transaction)).getMessage();


        assertEquals("Invalid card number", exceptionMessage);
    }

    @Test
    public void throwsException_whenCardPinIncorrectForExistingUser() {

        Booking booking = new Booking("05-01-97", "12:00", List.of("2A", "2B"));
        Transaction transaction = new Transaction(30.00, "343242", "0000");

        String exceptionMessage = assertThrows(InvalidPinNumberException.class, () -> customerService.buyTicket(
                "The Matrix",
                booking,
                transaction)).getMessage();


        assertEquals("Invalid pin number", exceptionMessage);
    }

    @Test
    public void throwsExceptionWhenBuyTicketForNonExistingMovie() {

        Booking booking = new Booking("05-01-97", "12:00", List.of("2A", "2B"));
        Transaction transaction = new Transaction(30.00, "343242", "1234");


        String exceptionMessage = assertThrows(NoSuchMovieException.class,
                () -> customerService.buyTicket(
                        "Non_Existent_Movie",
                        booking,
                        transaction
                )).getMessage();

        assertEquals("Movie not found: Non_Existent_Movie", exceptionMessage);
    }
}
