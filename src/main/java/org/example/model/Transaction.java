package org.example.model;

public class Transaction {

    private final Double totalCost;
    private final String cardNumber;
    private final String cardPin;

    public Transaction(Double totalCost, String cardNumber, String cardPin) {
        this.totalCost = totalCost;
        this.cardNumber = cardNumber;
        this.cardPin = cardPin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardPin() {
        return cardPin;
    }
}
