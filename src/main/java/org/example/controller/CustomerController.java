package org.example.controller;

import org.example.exception.NoSuchAccountException;
import org.example.exception.NoSuchMovieException;
import org.example.exception.NoSuchSeatException;
import org.example.model.*;
import org.example.service.CinemaService;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.utility.InputReader;
import org.example.utility.Utility;
import org.example.validator.CardValidator;
import org.example.validator.SeatValidator;
import org.example.validator.TicketValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CustomerController {
    boolean ticketAgentRights;
    private final Account sessionAccount;
    private final CustomerService customerService;
    private final MovieService movieService;
    private final CinemaService cinemaService;
    private final List<String> movieOptions = List.of(
            "1 - Buy tickets",
            "2 - Go back"
    );

    public CustomerController(Account sessionAccount, boolean ticketAgentRights) {
        this.ticketAgentRights = ticketAgentRights;
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
                Utility.printMessage("Logging off...");
                break;
            default:
                break;
        }
    }

    private void getMoviesController() {
        Utility.printHeaderMessage("Movies available:");
        movieService.getMovies()
                .forEach(x -> {
                    Utility.printSpace();
                    System.out.println("Movie: " + x.getName());
                    Utility.printMessage("Date: " + x.getAvailableDates());
                    Utility.printMessage("Time: " + x.getAvailableTimes());
                });
    }

    private void buyTicketsController() {
        boolean madeDecision = false;
        boolean pickedValidSeat = false;
        boolean madePayment = false;

        Movie movie;
        Booking booking;
        List<String> seats = new ArrayList<>();

        while (!madeDecision) {

            String option = Utility.outputMenuAndReadResponse(movieOptions);
            if (option.equals("2")) {
                break;
            }

            Utility.printMessage("Please enter the name of the movie you'd like to buy tickets for");
            String movieName = InputReader.read();
            Utility.printMessage("Please enter the date you'd like to watch the movie in 'dd-MM-yy' format");
            String date = InputReader.read();
            Utility.printMessage("Please enter the time you'd like to watch the movie in 'hh:mm' 24h format");
            String time = InputReader.read();

            Utility.printSpace();
            try {
                TicketValidator.validateFields(movieName, time, date);
                movie = movieService.getMovieByName(movieName);
                movieService.checkMovieExistsForDateAndTime(movie, date, time);
                cinemaService.populateSeats(movie, date, time);
            } catch (IllegalArgumentException | NoSuchMovieException e) {
                Utility.printMessage(e.getMessage());
                continue;
            }

            cinemaService.displaySeats();

            while(!pickedValidSeat){

                Utility.printSpace();
                Utility.printMessage("Please pick the seats you'd like to buy, seperated by commas: (Number first, then Letter - ie. 3F)");
                String seatInput = InputReader.read();
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

            if (!ticketAgentRights) {
                while(!madePayment){

                    Utility.printHeaderMessage("Payment portal:");
                    Utility.printMessage("Please enter your card number:");
                    String cardNumber = InputReader.read();

                    Utility.printMessage("Please enter your card pin:");
                    String cardPin = InputReader.read();

                    try{
                        CardValidator.validate(cardNumber, cardPin);
                        Booking newBooking = new Booking(date, time, seats);
                        Transaction transaction = new Transaction(movie.getPrice() * seats.size(), cardNumber, cardPin);

                        customerService.buyTicket(movieName, newBooking, transaction);
                    } catch (NoSuchAccountException | IllegalArgumentException e) {
                        Utility.printMessage(e.getMessage());
                        Utility.printMessage("Please try again");
                        continue;
                    }
                    madePayment = true;
                }
            } else {
                try{
                    Booking newBooking = new Booking(date, time, seats);
                    customerService.addTicket(movieName, newBooking);
                } catch (RuntimeException e) {
                    Utility.printMessage(e.getMessage());
                    continue;
                }
            }

            try{
                seats.forEach(cinemaService::bookSeat);
                movieService.addBooking(movieName, new Booking(date,time,seats));
            } catch (IllegalArgumentException e) {
                Utility.printMessage(e.getMessage());
                continue;
            }

            Utility.printSpace();
            Utility.printMessage("Tickets bought successfully");
            Utility.printMessage("Ticket bought for: " + movieName + " on " + date + " at " + time + " for seats: " + seats);
            Utility.printSpace();

            cinemaService.displaySeats();

            seats.clear();
            madeDecision = true;
        }
    }

    private void getTicketsController() {
        List<Ticket> tickets = customerService.getTickets(sessionAccount.getUserId());

        Utility.printHeaderMessage("Your tickets:");
        if (tickets.isEmpty()) Utility.printMessage("No tickets found");
        tickets.forEach(x -> Utility.printMessage(x.toString()));
    }
}
