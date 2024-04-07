package org.example.service;

import org.example.exception.NoSuchMovieException;
import org.example.exception.NoSuchSeatException;
import org.example.model.Booking;
import org.example.model.Movie;
import org.example.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class CinemaService {
    private List<Seat> seats;
    private final int numRows;
    private final int numSeatsPerRow;

    public CinemaService(int numRows, int numSeatsPerRow) {
        this.numRows = numRows;
        this.numSeatsPerRow = numSeatsPerRow;
        seats = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
            for (int j = 1; j <= numSeatsPerRow; j++) {
                seats.add(new Seat(i, j));
            }
        }
    }

    public void displaySeats() {
        System.out.println("Seating Arrangement:");
        System.out.println("====================");
        System.out.print("  "); // Add space for row labels
        for (int i = 1; i <= numSeatsPerRow; i++) {
            System.out.print(" " + (char) ('A' + i - 1) + "  "); // Display column labels (A, B, C, ...)
        }
        System.out.println();
        for (int i = 1; i <= numRows; i++) {
            System.out.print(i + " "); // Display row number
            for (int j = 1; j <= numSeatsPerRow; j++) {
                for (Seat seat : seats) {
                    if (seat.getRowNumber() == i && seat.getSeatNumber() == j) {
                        if (seat.isBooked()) {
                            System.out.print("[X] "); // Booked seat
                        } else {
                            System.out.print("[ ] "); // Available seat
                        }
                        break;
                    }
                }
            }
            System.out.println(); // Move to the next row
        }
    }

    public void bookSeat(String seat) {
        int row = Integer.parseInt(seat.substring(0, 1));
        int column = columnLabelToSeatNumber(seat.substring(1));

        seats.stream()
                .filter(s -> s.getRowNumber() == row && s.getSeatNumber() == column)
                .findFirst()
                .ifPresentOrElse(x -> {
                            if (x.isBooked()) {
                                throw new IllegalArgumentException("Seat " + seat + " already booked");
                            }
                            x.book();
                        }, () -> {
                            throw new IllegalArgumentException("Seat not found");
                        }
                );
    }
    private int columnLabelToSeatNumber(String columnLabel) {
        return columnLabel.toUpperCase().charAt(0) - 'A' + 1;
    }

    public void populateSeats(Movie movie, String date, String time) {

        seats.forEach(Seat::unbook);

        movie.getBookings()
                .stream()
                .filter(booking -> booking.getDate().equals(date) && booking.getTime().equals(time))
                .forEach(booking -> {
                    booking.getSeats()
                            .forEach(seat -> {
                                bookSeat(seat);
                            });
                });
    }

    public void checkSeatAvailability(Movie movie, Booking booking) {
        int row = Integer.parseInt(booking.getSeats().get(0).substring(0, 1));
        int column = columnLabelToSeatNumber(booking.getSeats().get(0).substring(1));

        if (row > numRows || column > numSeatsPerRow) {
            throw new NoSuchSeatException("Seat " + booking.getSeats().get(0) + " does not exist");
        }

        movie.getBookings().stream()
                .filter(x -> x.getDate().equals(booking.getDate()) && x.getTime().equals(booking.getTime()))
                .findFirst()
                .ifPresent(x -> {
                    x.getSeats()
                            .stream()
                            .filter(seat -> booking.getSeats().contains(seat))
                            .findAny()
                            .ifPresent(seat -> {
                                throw new NoSuchSeatException("Seat " + seat + " already booked");
                            });
                });

    }
}

