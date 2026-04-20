package org.example.currency_exchange.exception;

public class CurrencyWithThisCodeExistsException extends RuntimeException {
    public CurrencyWithThisCodeExistsException(String message) {
        super(message);
    }
}
