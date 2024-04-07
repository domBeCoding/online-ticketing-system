package org.example.model;

import java.util.List;
import java.util.Objects;

public class Ticket {
    private String movie;
    private String time;
    private String date;
    private Double price;
    private List<String> seats;

    public Ticket(String movie, String time, String date, List<String> seats, Double price) {
        this.movie = movie;
        this.time = time;
        this.date = date;
        this.seats = seats;
        this.price = price;
    }

    public Ticket() {
    }

    public String getMovie() {
        return movie;
    }

    public List<String> getSeats() {
        return seats;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "movie='" + movie + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", seats=" + seats +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Ticket ticket = (Ticket) object;
        return Objects.equals(movie, ticket.movie) && Objects.equals(time, ticket.time) && Objects.equals(date, ticket.date) && Objects.equals(price, ticket.price) && Objects.equals(seats, ticket.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, time, date, price, seats);
    }
}
