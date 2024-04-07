package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private String name;
    private String email;
    private String userId;
    private String password;
    private int cardNumber;
    private int pin;

    public Account(String name, String email, String userId, String password, int cardNumber, int pin) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public Account() {
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", cardNumber=" + cardNumber +
                ", pin=" + pin +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Account account = (Account) object;
        return cardNumber == account.cardNumber && pin == account.pin && Objects.equals(name, account.name) && Objects.equals(email, account.email) && Objects.equals(userId, account.userId) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, userId, password, cardNumber, pin);
    }
}
