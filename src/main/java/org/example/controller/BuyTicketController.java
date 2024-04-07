package org.example.controller;

import org.example.exception.NoSuchAccountException;
import org.example.exception.NoSuchMovieException;
import org.example.exception.NoSuchSeatException;
import org.example.model.*;
import org.example.service.CinemaService;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.validator.CardValidator;
import org.example.validator.SeatValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.utility.InputReader.read;
import static org.example.utility.Utility.*;
import static org.example.validator.TicketValidator.validateFields;

public class BuyTicketController {
    private final Account sessionAccount;
    private final CustomerService customerService;
    private final MovieService movieService;
    private final CinemaService cinemaService;
    private final List<String> movieOptions = List.of(
            "1 - Buy tickets",
            "2 - Go back"
    );

    public BuyTicketController(Account sessionAccount) {
        this.sessionAccount = sessionAccount;
        customerService = new CustomerService((Customer)sessionAccount);
        movieService = new MovieService();
        cinemaService = new CinemaService(5, 10);
    }

    public void routeOption(String option) {

        switch (option) {
            case "1":
                getMoviesController();
                buyTicketsController();
                break;
            case "2":
                getTicketsController();
                break;
            case "3":
                printMessage("Logging off...");
                break;
            default:
                break;
        }
    }

    private void buyTicketsController() {
        boolean madeDecision = false;
        boolean pickedValidSeat = false;
        boolean madePayment = false;

        Movie movie;
        Booking booking;
        List<String> seats = new ArrayList<>();

        while (!madeDecision) {

            String option = outputMenuAndReadResponse(movieOptions);
            if (option.equals("2")) {
                break;
            }

            printMessage("Please enter the name of the movie you'd like to buy tickets for");
            String movieName = read();
            printMessage("Please enter the date you'd like to watch the movie in 'dd-MM-yy' format");
            String date = read();
            printMessage("Please enter the time you'd like to watch the movie in 'hh:mm' 24h format");
            String time = read();

            printSpace();
            try {
                validateFields(movieName, time, date);
                movie = movieService.getMovieByName(movieName);
                movieService.checkMovieExistsForDateAndTime(movie, date, time);
                cinemaService.populateSeats(movie, date, time);
            } catch (IllegalArgumentException | NoSuchMovieException e) {
                printMessage(e.getMessage());
                continue;
            }

            cinemaService.displaySeats();

            while(!pickedValidSeat){

                printSpace();
                printMessage("Please pick the seats you'd like to buy, seperated by commas: (Number first, then Letter - ie. 3F)");
                String seatInput = read();
                try{
                    seats.addAll(Arrays.stream(seatInput.trim().split(","))
                            .map(String::trim)
                            .toList());

                    seats.forEach(SeatValidator::validate);

                    booking = new Booking(date, time, seats);
                    cinemaService.checkSeatAvailability(movie, booking);
                    pickedValidSeat = true;
                } catch (NoSuchSeatException e) {
                    seats.clear();
                    System.out.println(e.getMessage());
                }
            }

            while(!madePayment){

                printHeaderMessage("Payment portal:");
                printMessage("Please enter your card number:");
                String cardNumber = read();

                printMessage("Please enter your card pin:");
                String cardPin = read();

                try{
                    CardValidator.validate(cardNumber, cardPin);
                    Booking newBooking = new Booking(date, time, seats);
                    Transaction transaction = new Transaction(movie.getPrice() * seats.size(), cardNumber, cardPin);

                    customerService.buyTicket(movieName, newBooking, transaction);
                    seats.forEach(cinemaService::bookSeat);
                    movieService.addBooking(movieName, new Booking(date,time,seats));
                } catch (NoSuchAccountException | IllegalArgumentException e) {
                    printMessage(e.getMessage());
                    printMessage("Please try again");
                    continue;
                }
                madePayment = true;
            }


            printSpace();
            printMessage("Tickets bought successfully");
            printMessage("Ticket bought for: " + movieName + " on " + date + " at " + time + " for seats: " + seats);
            printSpace();

            cinemaService.displaySeats();

            seats.clear();
            madeDecision = true;
        }
    }

    private void getTicketsController() {
        List<Ticket> tickets = customerService.getTickets(sessionAccount.getUserId());

        printHeaderMessage("Your tickets:");
        if (tickets.isEmpty()) printMessage("No tickets found");
        tickets.forEach(x -> printMessage(x.toString()));
    }

    private void getMoviesController() {
        printHeaderMessage("Movies available:");
        movieService.getMovies()
                .forEach(x -> {
                    printSpace();
                    System.out.println("Movie: " + x.getName());
                    printMessage("Date: " + x.getAvailableDates());
                    printMessage("Time: " + x.getAvailableTimes());
                });
    }
}
