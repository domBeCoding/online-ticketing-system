package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer extends Account {
    private List<Ticket> purchasedTickets;

    public Customer(String name, String email, String userId, String password, int cardNumber, int pin) {
        super(name, email, userId, password, cardNumber, pin);
        this.purchasedTickets = new ArrayList<>();
    }

    public Customer() {
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }


    public void addPurchasedTickets(Ticket ticket) {
        this.purchasedTickets.add(ticket);
    }
}
