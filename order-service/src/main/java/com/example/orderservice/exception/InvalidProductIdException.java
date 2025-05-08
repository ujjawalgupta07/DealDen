package com.example.orderservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidProductIdException extends Exception{

    public InvalidProductIdException(String message) {
        super(message);
    }
}
