package org.example.utility;

import java.util.Scanner;

public class InputReader {

    private static Scanner scanner = new Scanner(System.in);

    public static String read(){

        return scanner.nextLine().trim();
    }

//    public static int readInt(){
//        return scanner.nextLine();
//    }

    public static String readWithSetOptions(int options){

        //TODO test what happens if String passed in
        String input = scanner.nextLine().trim();
        int i = Integer.parseInt(input);

        if(i > options || i == 0){
            throw new RuntimeException();
        }

        return input;
    }

    public static boolean readYesOrNo(){

        String input = scanner.nextLine().trim();
        boolean inputBoolean;

        switch(input){
            case "y", "Y":
                inputBoolean = true;
                break;
            case "n", "N":
                inputBoolean = false;
                break;
            default:
                inputBoolean = false;
                break;
        }

        return inputBoolean;
    }
}
