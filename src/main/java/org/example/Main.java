package org.example;

import org.example.controller.AccountController;
import org.example.model.Account;
import org.example.service.LoginService;
import org.example.service.LoginServiceBuilder;


import static org.example.utility.Utility.printHeaderMessage;
import static org.example.controller.LoginController.inputCredentialsPrompt;
import static org.example.controller.LoginController.selectUserLoginPrompt;

public class Main {

    public static void main(String[] args) {
        Main app = new Main();

//        printSpace();
//        Cinema cinema = new Cinema(5, 10);
//        cinema.populateSeats();
//        cinema.displaySeats();


        app.start();
    }

    private LoginService loginService;
    private AccountController accountController;
    public static Account sessionAccount;
    private String accountType = "";

    public void start(){

    printHeaderMessage("Hello, welcome to the Online Ticketing System portal");

    accountType = selectUserLoginPrompt();
    loginService = LoginServiceBuilder.build(accountType);

    inputCredentialsPrompt(loginService);
    sessionAccount = loginService.getAccount();

    printHeaderMessage("Welcome " + sessionAccount.getName() + ", you have logged in as a " + sessionAccount.getClass().getSimpleName());
    accountController = new AccountController(sessionAccount);
    accountController.selectAccountMenuPrompt();


    }


}