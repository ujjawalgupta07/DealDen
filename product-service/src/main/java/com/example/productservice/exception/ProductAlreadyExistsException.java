package com.example.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductAlreadyExistsException extends Exception{

    public ProductAlreadyExistsException(String message){
        super(message);
    }
}
