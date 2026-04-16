package org.example.currency_exchange.exception;

import java.io.IOException;

public class CurrencyNotFoundException extends IOException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
