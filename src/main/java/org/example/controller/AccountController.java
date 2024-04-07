package org.example.controller;

import org.example.model.Account;
import org.example.model.Customer;
import org.example.model.TicketAgent;
import org.example.model.VenueManager;

import java.util.List;

import static org.example.utility.Utility.*;

public class AccountController {
    private final Account sessionAccount;
    private final VenueManagerController venueManagerController;
    private final TicketAgentController ticketAgentController;
    private CustomerController customerController;
    private boolean loggedIn = true;
    public AccountController(Account account) {
        this.sessionAccount = account;
        venueManagerController = new VenueManagerController();
        ticketAgentController = new TicketAgentController();
    }

    private final List<String> customerOptions = List.of(
            "1 - See what's on",
            "2 - Your tickets",
            "3 - Log off"
    );
    private final List<String> ticketAgentOptions = List.of(
            "1 - Buy for customer",
            "2 - Customer's tickets",
            "3 - Sale statistics",
            "4 - Log off"
    );
    private final List<String> venueManagerOptions = List.of(
            "1 - Add a movie",
            "2 - Remove a movie",
            "3 - Edit a movie",
            "4 - Log off"
    );

    public void selectAccountMenuPrompt() {

        if(sessionAccount instanceof Customer){

            while(loggedIn){
                customerController = new CustomerController(sessionAccount, false);
                String option = outputMenuAndReadResponse(customerOptions);
                customerController.routeOption(option);
                if(option.equals("3")) loggedIn = false;
            }
        } else if (sessionAccount instanceof TicketAgent){

            while(loggedIn){
                String option = outputMenuAndReadResponse(ticketAgentOptions);
                ticketAgentController.routeOption(option);
                if(option.equals("4")) loggedIn = false;
            }
        } else if (sessionAccount instanceof VenueManager){

            while(loggedIn){
                String option = outputMenuAndReadResponse(venueManagerOptions);
                venueManagerController.routeOption(option);
                if(option.equals("4")) loggedIn = false;
            }
        }
    }
}
