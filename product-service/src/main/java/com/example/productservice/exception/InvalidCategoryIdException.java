package com.example.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCategoryIdException extends Exception{

    public InvalidCategoryIdException(String message) {
        super(message);
    }
}
