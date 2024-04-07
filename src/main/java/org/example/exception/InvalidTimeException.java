package org.example.exception;

public class InvalidTimeException extends IllegalArgumentException{
    public InvalidTimeException(String message){
        super(message);
    }
}
