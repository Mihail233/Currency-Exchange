package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class CurrencyPairWithThisCodeAlreadyExists extends IOException {
    public CurrencyPairWithThisCodeAlreadyExists(String message) {
        super(message);
    }
}
