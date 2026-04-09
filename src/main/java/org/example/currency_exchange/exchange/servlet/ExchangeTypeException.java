package org.example.currency_exchange.exchange.servlet;

import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

public enum ExchangeTypeException implements TypeException {
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    RequiredQueryParametersMissException(new ResponseEntity(400, "The required query parameter is missing")),
    ExchangeRateNotFoundException(new ResponseEntity(404, "Exchange rate not found for the pair"));
    private final ResponseEntity responseEntity;

    ExchangeTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
