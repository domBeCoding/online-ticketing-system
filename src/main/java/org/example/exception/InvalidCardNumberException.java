package org.example.exception;

public class InvalidCardNumberException extends IllegalArgumentException{
    public InvalidCardNumberException(String message){
        super(message);
    }
}
