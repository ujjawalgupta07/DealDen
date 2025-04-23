package com.example.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryAlreadyExistsException extends Exception{

    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
