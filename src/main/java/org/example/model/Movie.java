package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie{
    private String name;
    private Double price;
    private int length;
    private int rating;
    private List<String> availableTimes;
    private List<String> availableDates;
    private List<Booking> bookings;

    public Movie(String name, List<String> availableTimes, List<String> availableDates,int length, int rating, Double price) {
        this.name = name;
        this.availableTimes = availableTimes;
        this.availableDates = availableDates;
        this.length = length;
        this.rating = rating;
        this.price = price;
        this.bookings = new ArrayList<>();
    }

    public Movie() {
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public List<String> getAvailableDates() {
        return availableDates;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getRating() {
        return rating;
    }

    public Double getPrice() {
        return price;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public void setAvailableDates(List<String> availableDates) {
        this.availableDates = availableDates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Movie movie = (Movie) object;
        return length == movie.length && rating == movie.rating && Objects.equals(name, movie.name) && Objects.equals(price, movie.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length, rating, price);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Movie movie = (Movie) o;
//        return length == movie.length && rating == movie.rating && Objects.equals(name, movie.name);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(name, length, rating);
//    }

}
