package org.example.exception;

public class InvalidNumberException extends IllegalArgumentException{
    public InvalidNumberException(String message){
        super(message);
    }
}
