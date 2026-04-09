package org.example.currency_exchange.currency.mapper;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.Currency;

public class CurrencyMapper implements ObjectDtoMapper<Currency, CurrencyDTO> {

    @Override
    public CurrencyDTO objectToDto(Currency currency) {
        return new CurrencyDTO(currency.getId(), currency.getName(), currency.getCode(), currency.getSign());
    }

    @Override
    public Currency dtoToObject(CurrencyDTO currencyDTO) {
        return null;
    }
}
