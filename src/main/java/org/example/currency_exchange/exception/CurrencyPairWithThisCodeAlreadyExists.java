package org.example.currency_exchange.exception;

public class CurrencyPairWithThisCodeAlreadyExists extends RuntimeException {
    public CurrencyPairWithThisCodeAlreadyExists(String message) {
        super(message);
    }
}
