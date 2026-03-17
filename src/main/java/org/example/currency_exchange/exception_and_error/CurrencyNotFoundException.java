package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class CurrencyNotFoundException extends IOException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
