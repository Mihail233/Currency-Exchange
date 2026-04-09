package org.example.currency_exchange.currency.servlet.currencies_servlet;

import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

public enum CurrenciesServletTypeException implements TypeException {
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    RequiredFormFieldMissException(new ResponseEntity(400, "A required form field is missing")),
    CurrencyWithThisCodeExistsException(new ResponseEntity(409, "A currency with this code exists"));
    private final ResponseEntity responseEntity;

    CurrenciesServletTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
