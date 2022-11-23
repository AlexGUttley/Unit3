package org.example.service;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String errorMessage) {
        super(errorMessage);
    }
}
