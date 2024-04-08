package org.example.deleter;

import org.example.model.Customer;

public class CustomerDeleter extends FileDeleter<Customer> {
    public CustomerDeleter() {
        super(Customer.class);
    }
}
