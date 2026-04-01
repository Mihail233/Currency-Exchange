package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class InvalidParameterInPathException extends IOException {
    public InvalidParameterInPathException(String message) {
        super(message);
    }
}
