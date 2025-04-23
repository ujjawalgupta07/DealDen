package com.example.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryNotFoundException extends Exception{

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
