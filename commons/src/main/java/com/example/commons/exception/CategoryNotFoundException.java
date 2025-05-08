package com.example.commons.exception;

public class CategoryNotFoundException extends Exception{

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
    }
}
