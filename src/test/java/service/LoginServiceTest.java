package service;

import org.example.model.*;
import org.example.service.ManagementService;
import org.example.service.LoginService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    static ManagementService service = new ManagementService();

    @BeforeEach
    public void setup(){
        service.removeAll();
    }


    @Test
    public void onIncorrectCustomerCredentialsReturnFalse(){

        service.addCustomers(List.of(
                new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242,1234)
                ));

        LoginService<Customer> loginService = new LoginService(Customer.class);
        Credentials credentials = new Credentials("Matt_user", "password");
        boolean loginResult = loginService.login(credentials);

        assertFalse(loginResult);
    }

    @Test
    public void onCorrectCustomerCredentialsReturnTrue(){

        service.addCustomers(List.of(
                new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234)
        ));

        LoginService<Customer> loginService = new LoginService(Customer.class);

        Credentials credentials = new Credentials("Dominic_user", "password");
        boolean loginResult = loginService.login(credentials);

        assertTrue(loginResult);
    }

    @Test
    public void onCorrectVenueManagerCredentialsReturnTrue(){

        LoginService<VenueManager> loginService = new LoginService(VenueManager.class);
        Credentials credentials = new Credentials("Steve_user", "password");
        boolean loginResult = loginService.login(credentials);

        assertTrue(loginResult);
    }

    @Test
    public void onIncorrectVenueManagerCredentialsReturnFalse(){
        LoginService<VenueManager> loginService = new LoginService(VenueManager.class);

        Credentials credentials = new Credentials("non_existent_user", "sdfdsf");
        boolean loginResult = loginService.login(credentials);

        assertFalse(loginResult);
    }

    @Test
    public void onCorrectTicketAgentCredentialsReturnTrue(){

        LoginService<TicketAgent> loginService = new LoginService(TicketAgent.class);
        Credentials credentials = new Credentials("John_user", "password");
        boolean loginResult = loginService.login(credentials);

        assertTrue(loginResult);
    }

    @Test
    public void onIncorrectTicketAgentCredentialsReturnFalse(){
        LoginService<TicketAgent> loginService = new LoginService(TicketAgent.class);

        Credentials credentials = new Credentials("non_existent_user", "sdfdsf");
        boolean loginResult = loginService.login(credentials);

        assertFalse(loginResult);
    }
}
