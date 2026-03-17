package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class UnknownException extends IOException {
    public UnknownException(String message) {
        super(message);
    }
}
