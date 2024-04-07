package org.example.exception;

public class NoSuchFieldException extends RuntimeException{
    public NoSuchFieldException(String message){
        super(message);
    }
}
