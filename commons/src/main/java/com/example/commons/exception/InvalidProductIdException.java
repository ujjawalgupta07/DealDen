package com.example.commons.exception;

public class InvalidProductIdException extends Exception{

    public InvalidProductIdException(String message) {
        super(message);
    }

    public InvalidProductIdException() {
    }
}
