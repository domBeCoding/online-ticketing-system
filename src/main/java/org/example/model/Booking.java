package org.example.model;

import java.util.List;
import java.util.Objects;

public class Booking {
    private String date;
    private String time;
    private List<String> seats;

    public Booking(String date, String time, List<String> seats){
        this.date = date;
        this.time = time;
        this.seats = seats;
    }
    public Booking() {
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<String> getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", seats=" + seats +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Booking booking = (Booking) object;
        return Objects.equals(date, booking.date) && Objects.equals(time, booking.time) && Objects.equals(seats, booking.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, seats);
    }
}
