package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class RequiredFormFieldMissException extends IOException {
    public RequiredFormFieldMissException(String message) {
        super(message);
    }
}
