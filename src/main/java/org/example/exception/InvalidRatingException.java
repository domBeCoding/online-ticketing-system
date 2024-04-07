package org.example.exception;

public class InvalidRatingException extends IllegalArgumentException{
    public InvalidRatingException(String message){
        super(message);
    }
}
