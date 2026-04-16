package org.example.currency_exchange.exception;

import java.io.IOException;

public class RequiredQueryParametersMissException extends IOException {
    public RequiredQueryParametersMissException(String message) {
        super(message);
    }
}
