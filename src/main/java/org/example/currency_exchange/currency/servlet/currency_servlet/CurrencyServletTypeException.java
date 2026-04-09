package org.example.currency_exchange.currency.servlet.currency_servlet;

import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

public enum CurrencyServletTypeException implements TypeException {
    //InvalidCurrencyCodeInPathException - даже если 2 кода в строке, то ошибки что пропущен код
    InvalidParameterInPathException(new ResponseEntity(400, "Currency code is missing from the address")),
    CurrencyNotFoundException(new ResponseEntity(404, "Currency not found")),
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable"));
    private final ResponseEntity responseEntity;

    CurrencyServletTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
