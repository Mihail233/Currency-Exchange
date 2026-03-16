package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class DataBaseUnavailableException extends IOException {
    public DataBaseUnavailableException(String message) {
        super(message);
    }
}
