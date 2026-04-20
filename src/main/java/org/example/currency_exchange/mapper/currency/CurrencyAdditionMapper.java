package org.example.currency_exchange.mapper.currency;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyRequestDTO;

public class CurrencyAdditionMapper implements ObjectDtoMapper<Currency, CurrencyRequestDTO> {
    @Override
    public CurrencyRequestDTO objectToDto(Currency currency) {
        return new CurrencyRequestDTO(currency.name(), currency.code(), currency.sign());
    }

    @Override
    public Currency dtoToObject(CurrencyRequestDTO currencyRequestDTO) {
        return new Currency(null, currencyRequestDTO.name(), currencyRequestDTO.code(), currencyRequestDTO.sign());
    }
}
