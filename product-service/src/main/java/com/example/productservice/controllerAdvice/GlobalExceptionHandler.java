package com.example.productservice.controllerAdvice;

import com.example.productservice.dto.response.ErrorDTO;
import com.example.productservice.exception.CategoryAlreadyExistsException;
import com.example.productservice.exception.InvalidProductIdException;
import com.example.productservice.exception.ProductAlreadyExistsException;
import com.example.productservice.exception.ProductNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException exception){
        ErrorDTO dto =
                ErrorDTO.builder()
                .code("product_not_found")
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(InvalidProductIdException exception){
        ErrorDTO dto =
                ErrorDTO.builder()
                        .code("invalid_product_id")
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(BadRequestException exception){
        ErrorDTO dto =
                ErrorDTO.builder()
                        .code("bad_request")
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(ProductAlreadyExistsException exception){
        ErrorDTO dto =
                ErrorDTO.builder()
                        .code("product_already_exists")
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(dto, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(CategoryAlreadyExistsException exception){
        ErrorDTO dto =
                ErrorDTO.builder()
                        .code("category_already_exists")
                        .message(exception.getMessage())
                        .build();

        return new ResponseEntity<>(dto, HttpStatus.ALREADY_REPORTED);
    }

}
