package com.casas.casas.domain.exceptions;

public class ListingDateExceedTimeException extends RuntimeException {
    public ListingDateExceedTimeException(String message) {
        super(message);
    }
}
