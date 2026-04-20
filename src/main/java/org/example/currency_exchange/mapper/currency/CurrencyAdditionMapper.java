package org.example.currency_exchange.mapper.currency;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyAdditionDTO;

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
