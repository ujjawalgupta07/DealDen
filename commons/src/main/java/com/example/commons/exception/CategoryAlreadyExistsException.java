package com.example.commons.exception;

public class CategoryAlreadyExistsException extends Exception{

    public CategoryAlreadyExistsException(String message){
        super(message);
    }

    public CategoryAlreadyExistsException() {
    }
}
