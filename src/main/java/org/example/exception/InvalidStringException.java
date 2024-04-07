package org.example.exception;

public class InvalidStringException extends IllegalArgumentException{
    public InvalidStringException(String message){
        super(message);
    }
}
