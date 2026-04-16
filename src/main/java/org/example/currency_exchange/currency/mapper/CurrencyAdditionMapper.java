package org.example.currency_exchange.currency.mapper;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;

public class CurrencyAdditionMapper implements ObjectDtoMapper<Currency, CurrencyAdditionDTO> {
    @Override
    public CurrencyAdditionDTO objectToDto(Currency currency) {
        return new CurrencyAdditionDTO(currency.getName(), currency.getCode(), currency.getSign());
    }

    @Override
    public Currency dtoToObject(CurrencyAdditionDTO currencyAdditionDTO) {
        return new Currency(null, currencyAdditionDTO.name(), currencyAdditionDTO.code(), currencyAdditionDTO.sign());
    }
}
