package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class ExchangeRateNotFoundException extends IOException {
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
