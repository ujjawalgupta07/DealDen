package com.example.commons.exception;

public class InvalidCategoryIdException extends Exception{

    public InvalidCategoryIdException(String message) {
        super(message);
    }

    public InvalidCategoryIdException() {
    }
}
