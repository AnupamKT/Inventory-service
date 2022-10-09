package com.example.inventoryservice.common;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(String msg) {
        super(msg);
    }
}
