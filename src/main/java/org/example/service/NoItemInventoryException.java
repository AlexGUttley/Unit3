package org.example.service;

public class NoItemInventoryException extends RuntimeException{
    public NoItemInventoryException(String errorMessage) {
        super(errorMessage);
    }
}
