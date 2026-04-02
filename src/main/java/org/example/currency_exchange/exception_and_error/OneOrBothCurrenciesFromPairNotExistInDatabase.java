package org.example.currency_exchange.exception_and_error;

import java.io.IOException;

public class OneOrBothCurrenciesFromPairNotExistInDatabase extends IOException {
    public OneOrBothCurrenciesFromPairNotExistInDatabase(String message) {
        super(message);
    }
}
