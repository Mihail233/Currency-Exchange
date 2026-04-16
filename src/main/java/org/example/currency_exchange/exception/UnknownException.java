package org.example.currency_exchange.exception;

import java.io.IOException;

public class UnknownException extends IOException {
    public UnknownException(String message) {
        super(message);
    }
}
