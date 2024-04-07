package org.example.deleter;

import org.example.model.Customer;

public class CustomerDeleter extends FileDeleter<Customer> {
    public CustomerDeleter() {
        super("/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/customer.json", Customer.class);
    }
}
