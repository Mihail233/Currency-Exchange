package org.example.currency_exchange.currency;

import org.example.currency_exchange.commons.ExceptionHandler;
import org.example.currency_exchange.commons.TypeException;


public class CurrencyExceptionHandler extends ExceptionHandler {
    private final static TypeException[] TYPE_EXCEPTIONS = CurrencyTypeException.values();

    public CurrencyExceptionHandler() {
        super(TYPE_EXCEPTIONS);
    }
}