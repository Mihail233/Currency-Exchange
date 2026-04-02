package org.example.currency_exchange.exchange_rates;

import lombok.Getter;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.TypeException;

@Getter
public enum ExchangeRateTypeException implements TypeException {
    DataBaseUnavailableException(new ResponseEntity(500, "The database is unavailable")),
    InvalidParameterInPathException(new ResponseEntity(400, "Currency pair codes are missing in the address")),
    ExchangeRateNotFoundException(new ResponseEntity(404,"Exchange rate not found for the pair")),
    UnknownException(new ResponseEntity(500, "Unknown error")),
    RequiredFormFieldMissException(new ResponseEntity(400, "A required form field is missing")),
    OneOrBothCurrenciesFromPairNotExistInDatabase(new ResponseEntity(404, "One (or both) currencies from the currency pair do not exist in the database")),
    CurrencyPairWithThisCodeAlreadyExists(new ResponseEntity(409, "A currency pair with this code already exists"));

    private final ResponseEntity responseEntity;

    ExchangeRateTypeException(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }
}
