package com.example.readwick.shared.exception;

public class IllegalSessionStateException extends RuntimeException {
    public IllegalSessionStateException(String message) {
        super(message);
    }
}
