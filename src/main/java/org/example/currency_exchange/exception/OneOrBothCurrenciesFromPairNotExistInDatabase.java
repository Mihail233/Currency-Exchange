package org.example.currency_exchange.exception;

import java.io.IOException;

public class OneOrBothCurrenciesFromPairNotExistInDatabase extends IOException {
    public OneOrBothCurrenciesFromPairNotExistInDatabase(String message) {
        super(message);
    }
}
