package org.example.currency_exchange.exception;

import java.io.IOException;

public class CurrencyPairWithThisCodeAlreadyExists extends IOException {
    public CurrencyPairWithThisCodeAlreadyExists(String message) {
        super(message);
    }
}
