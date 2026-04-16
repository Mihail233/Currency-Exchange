package org.example.currency_exchange.exchange_rate.servlet.exchange_rates_servlet;

import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.common.TypeException;

public enum ExchangeRatesTypeException implements TypeException {
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    RequiredFormFieldMissException(new ResponseEntity(400, "A required form field is missing")),
    CurrencyNotFoundException(new ResponseEntity(404, "One (or both) currencies from the currency pair do not exist in the database")),
    CurrencyPairWithThisCodeAlreadyExists(new ResponseEntity(409, "A currency pair with this code already exists"));
    private final ResponseEntity responseEntity;

    ExchangeRatesTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}