package org.example.currency_exchange.exception;

import java.io.IOException;

public class InvalidParameterInPathException extends IOException {
    public InvalidParameterInPathException(String message) {
        super(message);
    }
}
