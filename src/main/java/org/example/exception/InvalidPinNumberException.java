package org.example.exception;

public class InvalidPinNumberException extends IllegalArgumentException{
    public InvalidPinNumberException(String message){
        super(message);
    }
}
