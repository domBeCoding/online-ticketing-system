package org.example.model;

public class Seat {
    private int rowNumber;
    private int seatNumber;
    private boolean booked;

    public Seat(int rowNumber, int seatNumber) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.booked = false;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void book() {
        booked = true;
    }

    public void unbook() {
        booked = false;
    }
}

