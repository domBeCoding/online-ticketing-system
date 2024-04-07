package service;

import org.example.model.Customer;
import org.example.model.Transaction;
import org.example.service.PaymentService;
import org.junit.jupiter.api.Test;

public class PaymentServiceTest {
    @Test
    public void whenPayMethodCalledWithValidCardDetails_thenNoExceptionIsThrown() {
        PaymentService paymentService = new PaymentService();
        Customer customer = new Customer("Dominic", "dom@email.com", "Dominic_user", "password", 343242, 1234);
        Transaction transaction = new Transaction(30.00, "343242", "1234");

        paymentService.pay(customer, transaction);
    }
}
