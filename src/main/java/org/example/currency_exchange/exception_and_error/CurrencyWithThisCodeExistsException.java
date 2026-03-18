package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class CurrencyWithThisCodeExistsException extends IOException {
    public CurrencyWithThisCodeExistsException(String message) {
        super(message);
    }
}
