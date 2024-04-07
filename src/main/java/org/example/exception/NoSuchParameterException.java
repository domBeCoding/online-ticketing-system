package org.example.exception;

public class NoSuchParameterException extends NoSuchFieldException{

    public NoSuchParameterException(String s) {
        super(s);
    }
}
