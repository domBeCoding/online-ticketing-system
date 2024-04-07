package org.example.exception;

public class MovieDeletionException extends RuntimeException{
    public MovieDeletionException(String message){
        super(message);
    }
}
