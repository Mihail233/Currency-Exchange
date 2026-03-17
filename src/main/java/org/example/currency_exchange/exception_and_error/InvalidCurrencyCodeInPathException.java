package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class InvalidCurrencyCodeInPathException extends IOException {
    public InvalidCurrencyCodeInPathException(String message) {
        super(message);
    }
}
