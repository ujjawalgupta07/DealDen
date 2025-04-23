package com.example.productservice.dto.response;

import lombok.*;

public class ErrorDTO {

    private String message;
    private String code;

    public ErrorDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
