package org.example.currency_exchange.exchange_rates.servlet.exchange_rate_servlet;

import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

public enum ExchangeRateTypeException implements TypeException {
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    InvalidParameterInPathException(new ResponseEntity(400, "Currency pair codes are missing in the address")),
    ExchangeRateNotFoundException(new ResponseEntity(404,"Exchange rate not found for the pair")),
    RequiredFormFieldMissException(new ResponseEntity(400, "A required form field is missing")),
    CurrencyNotFoundException(new ResponseEntity(404, "One (or both) currencies from the currency pair do not exist in the database"));
    private final ResponseEntity responseEntity;

    ExchangeRateTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}

