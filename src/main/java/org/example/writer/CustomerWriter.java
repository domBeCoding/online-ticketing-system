package org.example.writer;

import org.example.exception.NoSuchAccountException;
import org.example.model.Customer;
import org.example.model.Ticket;

import java.io.File;
import java.io.IOException;

public class CustomerWriter extends FileWriter<Customer> {
    public CustomerWriter() {
        super(Customer.class);
    }

    public void addTicket(String userId, Ticket ticket) {
        try {
            list = mapper.readValue(new File(filePath), listType);

            list.stream()
                    .filter(x -> x.getUserId().equals(userId))
                    .findFirst()
                    .ifPresentOrElse(
                            x -> x.addPurchasedTickets(ticket),
                            () -> {
                                throw new NoSuchAccountException("Customer not found for userId: " + userId);
                            }
                    );

            mapper.writeValue(new File(filePath), list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
