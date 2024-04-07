package org.example.exception;

public class NoSuchMovieException extends NoSuchFieldException{


    public NoSuchMovieException(String s) {
        super(s);
    }
}
