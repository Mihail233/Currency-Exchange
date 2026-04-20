package org.example.currency_exchange.mapper.currency;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.dto.currency.CurrencyDTO;
import org.example.currency_exchange.entity.Currency;

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
