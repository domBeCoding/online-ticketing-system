package org.example.utility;

import java.util.List;

public class Utility<T> {

    public static void printSpace() {
        System.out.println("");
    }

    public static void printDivider(String message) {
        int dividerLength = (int) message.chars().count();

        StringBuilder divider = new StringBuilder();

        for (int i = 0; i < dividerLength; i++) {
            divider.append("=");
        }

        System.out.println(divider);
    }

    public static void printSubDivider(String message) {
        int dividerLength = (int) message.chars().count();

        StringBuilder divider = new StringBuilder();

        for (int i = 0; i < dividerLength; i++) {
            divider.append("-");
        }

        System.out.println(divider);
    }

    public static void printHeaderMessage(String message) {
        printSpace();
        printDivider(message);
        System.out.println(message);
        printDivider(message);
    }

    public static void printMessage(String message) {
        System.out.println("+ " + message);
    }

    public static String outputMenuAndReadResponse(List<String> options) {
        String menuSelection = "";
        boolean validOptionSelected = false;

        while (!validOptionSelected) {
            printSpace();
            System.out.println("Menu:");

            for (String option : options) {
                printMessage(option);
            }

            printSpace();
            printMessage("Please select your option");

            try {
                menuSelection = InputReader.readWithSetOptions(options.size());
                validOptionSelected = true;

            } catch (RuntimeException e) {
                printHeaderMessage("Invalid input, please try again");
            }
        }
        return menuSelection;
    }


}
