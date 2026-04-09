package org.example.currency_exchange;

import org.example.currency_exchange.commons.TypeException;

public enum AnotherTypeException implements TypeException {
    UnknownException(new ResponseEntity(500, "Unknown error"));

    private final ResponseEntity responseEntity;

    AnotherTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
