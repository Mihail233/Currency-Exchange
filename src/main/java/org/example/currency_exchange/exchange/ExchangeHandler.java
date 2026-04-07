package org.example.currency_exchange.exchange;

import org.example.currency_exchange.commons.ExceptionHandler;
import org.example.currency_exchange.commons.TypeException;

public class ExchangeHandler extends ExceptionHandler {
    private final static TypeException[] TYPE_EXCEPTIONS = ExchangeTypeException.values();

    public ExchangeHandler() {
        super(TYPE_EXCEPTIONS);
    }
}
