package com.casas.casas.domain.exceptions;

public class NullException extends RuntimeException {
    public NullException(String message) {
        super(message + " can not be null");
    }
}
