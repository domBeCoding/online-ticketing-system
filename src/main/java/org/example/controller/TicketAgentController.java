package org.example.controller;

import org.example.exception.NoSuchAccountException;
import org.example.service.ManagementService;

import static org.example.utility.InputReader.read;
import static org.example.utility.InputReader.readYesOrNo;
import static org.example.utility.Utility.printMessage;
import static org.example.utility.Utility.printSpace;

public class TicketAgentController {
    private ManagementService managementService = new ManagementService();
    private CustomerController customerController;

    public void routeOption(String option) {
        switch (option) {
            case "1":
                buyTicketsController();
                break;
            case "2":
                getTicketsController();
                break;
            case "3":
                getAllTickets();
                break;
            case "4":
                logOff();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void buyTicketsController() {
        boolean finishedBuying = false;

        while (!finishedBuying) {
            printMessage("Please enter the userId of the Customer you would like to buy tickets for");
            String userId = read();

            try {
                customerController = new CustomerController(managementService.getCustomerByUserId(userId), true);
                customerController.routeOption("1");
            } catch (NoSuchAccountException e) {
                System.out.println(e.getMessage());
                continue;
            }

            printSpace();
            printMessage("Would you like to buy tickets for another customer? (y/n)");
            if (readYesOrNo()) {
                continue;
            }

            finishedBuying = true;
        }
    }

    private void getTicketsController() {
        boolean finishedOperation = false;

        while (!finishedOperation) {
            printMessage("Please enter the userId of the Customer you would like to see tickets for");
            String userId = read();

            try {
                customerController = new CustomerController(managementService.getCustomerByUserId(userId), true);
                customerController.routeOption("2");
            } catch (NoSuchAccountException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
                continue;
            }

            printSpace();
            printMessage("Would you like to view tickets for another customer? (y/n)");
            if (readYesOrNo()) {
                continue;
            }
            ;

            finishedOperation = true;
        }
    }

    private void getAllTickets() {
        printSpace();
        printMessage("All Purchased tickets:");
        printSpace();
        managementService.getCustomers().forEach(customer -> {
            System.out.println(customer.getUserId() + ":");
            System.out.println(customer.getPurchasedTickets());
            printSpace();
        });
    }

    private void logOff() {
        System.out.println("Logging off");
    }
}
