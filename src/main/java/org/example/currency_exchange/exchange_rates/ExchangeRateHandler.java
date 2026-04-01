package org.example.currency_exchange.exchange_rates;

import org.example.currency_exchange.commons.ExceptionHandler;
import org.example.currency_exchange.commons.TypeException;

public class ExchangeRateHandler extends ExceptionHandler {
    private final static TypeException[] TYPE_EXCEPTIONS = ExchangeRateTypeException.values();

    public ExchangeRateHandler() {
        super(TYPE_EXCEPTIONS);
    }
}
