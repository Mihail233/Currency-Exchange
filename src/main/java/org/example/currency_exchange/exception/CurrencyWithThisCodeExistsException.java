package org.example.currency_exchange.exception;

import java.io.IOException;

public class CurrencyWithThisCodeExistsException extends IOException {
    public CurrencyWithThisCodeExistsException(String message) {
        super(message);
    }
}
