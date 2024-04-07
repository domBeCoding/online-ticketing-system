package org.example.controller;

import org.example.utility.InputReader;
import org.example.model.Credentials;
import org.example.service.LoginService;

import static org.example.utility.Utility.*;

public class LoginController {

    public static String selectUserLoginPrompt() {
        String sessionUserType = "";

        boolean validOptionSelected = false;

        while (!validOptionSelected) {
            printSpace();
            System.out.println("Menu:");
            printMessage("1 - Customer interface");
            printMessage("2 - Ticket agent management");
            printMessage("3 - Venue management");
            printSpace();
            printMessage("Please select a portal for login");

            try {
                sessionUserType = InputReader.readWithSetOptions(3);
                validOptionSelected = true;

            } catch (RuntimeException e) {
                printHeaderMessage("Invalid input, please try again");
            }
        }

        return sessionUserType;
    }

    public static void inputCredentialsPrompt(LoginService loginService) {
        String password;
        String sessionUserId;
        boolean isLoggedIn = false;

        while (!isLoggedIn) {
            printSpace();
            printMessage("To proceed, please enter your username:");
            sessionUserId = InputReader.read().trim();

            printMessage("Please enter you password:");
            password = InputReader.read().trim();

            boolean loginResult = loginService.login(new Credentials(sessionUserId, password));

            if (!loginResult) {
                printHeaderMessage("Your login credentials are invalid, please try again");
            }

            isLoggedIn = loginResult;
        }
    }
}
