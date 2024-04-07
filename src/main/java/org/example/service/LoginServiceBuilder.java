package org.example.service;

import org.example.model.Customer;
import org.example.model.TicketAgent;
import org.example.model.VenueManager;

public class LoginServiceBuilder {

    static LoginService loginService;

    public static LoginService build(String userType) {
        switch (userType) {
            case "1":
                loginService = new LoginService<>(Customer.class);
                break;
            case "2":
                loginService = new LoginService<>(TicketAgent.class);
                break;
            case "3":
                loginService = new LoginService<>(VenueManager.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userType);
        }

        return loginService;
    }

}
