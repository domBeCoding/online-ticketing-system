package org.example.model;

public class TicketAgent extends Account{

    public TicketAgent(String name, String email, String userId, String password) {
        super(name, email, userId, password, 0, 0);
    }

    public TicketAgent() {
    }
}
