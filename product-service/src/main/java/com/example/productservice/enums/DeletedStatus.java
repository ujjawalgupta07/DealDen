package com.example.productservice.enums;

public enum DeletedStatus {

    Y("N"),
    N("N");

    final String value;

    DeletedStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
