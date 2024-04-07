package org.example.reader;

import org.example.exception.NoSuchAccountException;
import org.example.exception.NoSuchMovieException;
import org.example.model.Account;
import org.example.model.Customer;
import org.example.model.Movie;
import org.example.model.Ticket;

import java.util.List;

import static org.example.Main.sessionAccount;

public class CustomerReader extends FileReader<Customer> {
    public CustomerReader() {
        super(Customer.class);
    }

    public List<Ticket> getTickets(String userId) {
        read();
        return objectList.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchAccountException("Customer not found: " + userId))
                .getPurchasedTickets();
    }

    public Customer get(String userId) {
        read();
        return objectList.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NoSuchAccountException("Customer not found: " + userId));
    }
}
