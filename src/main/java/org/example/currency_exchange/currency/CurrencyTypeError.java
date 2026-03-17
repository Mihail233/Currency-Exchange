package org.example.currency_exchange.currency;

import org.example.currency_exchange.ResponseEntity;

public enum CurrencyTypeError {
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    UnknownException(new ResponseEntity(500, "unknown error"));


    private final ResponseEntity responseEntity;
    CurrencyTypeError(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
