package org.example.currency_exchange.currency;

import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

public enum CurrencyTypeException implements TypeException {
    InvalidCurrencyCodeInPathException(new ResponseEntity(400, "Currency code is missing from the address")),
    CurrencyNotFoundException(new ResponseEntity(404, "Currency not found")),
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    UnknownException(new ResponseEntity(500, "Unknown error")),
    RequiredFormFieldMissException(new ResponseEntity(400, "A required form field is missing")),
    CurrencyWithThisCodeExists(new ResponseEntity(409, "Валюта с таким кодом существует"));

    private final ResponseEntity responseEntity;
    CurrencyTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
