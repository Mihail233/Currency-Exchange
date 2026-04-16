package org.example.currency_exchange.exception;

import java.io.IOException;

public class ExchangeRateNotFoundException extends IOException {
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
