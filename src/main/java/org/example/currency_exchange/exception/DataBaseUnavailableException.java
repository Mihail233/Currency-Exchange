package org.example.currency_exchange.exception;

public class DataBaseUnavailableException extends RuntimeException {
    public DataBaseUnavailableException(String message) {
        super(message);
    }
}
