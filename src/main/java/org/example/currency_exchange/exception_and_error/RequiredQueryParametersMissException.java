package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class RequiredQueryParametersMissException extends IOException {
    public RequiredQueryParametersMissException(String message) {
        super(message);
    }
}
