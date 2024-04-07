package service;

import org.example.service.ManagementService;
import org.example.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagementServiceTest {
    private final ManagementService managementService = new ManagementService();

    private final List<Customer> customers = List.of(
            new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234),
            new Customer("Zoe", "zoe@email.com", "Zoe_user", "password", 942483, 9876)
    );

    @BeforeEach
    public void setup() {
        managementService.removeAll();
        managementService.addCustomers(customers);
    }

    @Test
    public void addCustomersSuccessfully() {
        assertEquals(customers, managementService.getCustomers());
    }

    @Test
    public void successfullyDeleteAllCustomers() {
        managementService.removeAll();
        assertEquals(Collections.emptyList(), managementService.getCustomers());
    }

    @Test
    public void deleteCustomerSuccessfully() {
        List<Customer> customersToBeRemoved = List.of(
                new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234)

        );

        List<Customer> expectedCustomers = List.of(
                new Customer("Zoe", "zoe@email.com", "Zoe_user", "password", 942483, 9876)
        );

        managementService.removeCustomers(customersToBeRemoved);

        assertEquals(expectedCustomers, managementService.getCustomers());
    }

    @Test
    public void throwsExceptionsOnDeletionWhenCustomerNotPresent() {
        List<Customer> customersToBeRemoved = List.of(
                new Customer("non-existent", "dom@email.com", "Dominic_user", "password", 343242, 1234)

        );

        String message = assertThrows(
                RuntimeException.class,
                () -> managementService.removeCustomers(customersToBeRemoved)).getMessage();

        assertEquals("Delete failure: Following elements not present in database:" + customersToBeRemoved, message);
    }

    @Test
    public void throwExceptionIfAllAddedCustomersAlreadyExists() {
        List<Customer> existingCustomer = List.of(
                new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234)
        );

        String exception = assertThrows(RuntimeException.class, () -> managementService.addCustomers(existingCustomer)).getMessage();
        assertEquals(customers, managementService.getCustomers());
        assertEquals("Added elements all already exist in DB: " +
                "[Account{name='Dominic', email='dom@email.com', userId='Dominic_user', password='password', cardNumber=343242, pin=1234}]", exception);
    }

    @Test
    public void getCustomersByUserId() {
        new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234);

        Customer customer = assertDoesNotThrow(() -> managementService.getCustomerByUserId("Dominic_user"));
        assertEquals(
                new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234),
                customer
        );
    }
}
