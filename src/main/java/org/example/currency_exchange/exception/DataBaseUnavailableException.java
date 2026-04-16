package org.example.currency_exchange.exception;

import java.io.IOException;

public class DataBaseUnavailableException extends IOException {
    public DataBaseUnavailableException(String message) {
        super(message);
    }
}
