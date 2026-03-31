package org.example.currency_exchange.currency;

import lombok.Getter;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

@Getter
public enum CurrencyTypeException implements TypeException {
    //InvalidCurrencyCodeInPathException - даже если 2 кода в строке, то ошибки что пропущен код
    InvalidCurrencyCodeInPathException(new ResponseEntity(400, "Currency code is missing from the address")),
    CurrencyNotFoundException(new ResponseEntity(404, "Currency not found")),
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    UnknownException(new ResponseEntity(500, "Unknown error")),
    RequiredFormFieldMissException(new ResponseEntity(400, "A required form field is missing")),
    CurrencyWithThisCodeExistsException(new ResponseEntity(409, "A currency with this code exists"));

    private final ResponseEntity responseEntity;
    CurrencyTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }
}
