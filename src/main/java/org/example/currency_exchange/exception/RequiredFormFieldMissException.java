package org.example.currency_exchange.exception;

import java.io.IOException;

public class RequiredFormFieldMissException extends IOException {
    public RequiredFormFieldMissException(String message) {
        super(message);
    }
}
