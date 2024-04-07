package org.example.service;

import org.example.deleter.CustomerDeleter;
import org.example.model.Customer;
import org.example.reader.CustomerReader;
import org.example.writer.CustomerWriter;

import java.util.Collections;
import java.util.List;

public class ManagementService {
    private final CustomerDeleter deletor;
    private final CustomerWriter writer;
    private final CustomerReader reader;

    public ManagementService() {
        this.deletor = new CustomerDeleter();
        this.writer = new CustomerWriter();
        this.reader = new CustomerReader();
    }

    public List<Customer> getCustomers(){
        return reader.getAll();
    }

    public void addCustomers(List<Customer> customers){
        writer.append(customers);
    }

    public void removeCustomers(List<Customer> customers){
            deletor.remove(customers);
    }

    public void removeAll(){
        writer.overwrite(Collections.emptyList());
    }

    public Customer getCustomerByUserId(String userId){
        return reader.get(userId);
    }
}
