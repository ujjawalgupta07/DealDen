package com.example.productservice.controllerAdvice;

import com.example.productservice.dto.response.ErrorDTO;
import com.example.productservice.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("product_not_found");

        return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(InvalidProductIdException exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("invalid_product_id");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(BadRequestException exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("bad_request");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(ProductAlreadyExistsException exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("product_already_exists");

        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleInvalidProductIdException(CategoryAlreadyExistsException exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("category_already_exists");
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        String message = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(message);
        dto.setCode("request_validation_failed");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(AccessDeniedException exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("access_denied");
        return new ResponseEntity<>(dto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception exception){
        ErrorDTO dto = new ErrorDTO();
        dto.setMessage(exception.getMessage());
        dto.setCode("internal_server_error");
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
