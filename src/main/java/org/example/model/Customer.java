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


    //    private String name;
//    private String email;
//    private String userId;
//    private String password;
//    private int cardNumber;
//
//    public Customer(String name, String email, String userId, String password, int cardNumber) {
//        this.name = name;
//        this.email = email;
//        this.userId = userId;
//        this.password = password;
//        this.cardNumber = cardNumber;
//    }
//
//    public Customer() {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public int getCardNumber() {
//        return cardNumber;
//    }
//
//    @Override
//    public String toString() {
//        return "{userId=" + name + ", email=" + email + ", userId=" + userId + ", password=" + password + ", cardNumber=" + cardNumber + "}";
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (!(object instanceof Customer)) return false;
//        Customer customer = (Customer) object;
//        return cardNumber == customer.cardNumber && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(userId, customer.userId) && Objects.equals(password, customer.password);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, email, userId, password, cardNumber);
//    }
}
