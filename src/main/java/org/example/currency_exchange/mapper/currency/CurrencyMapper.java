package org.example.currency_exchange.mapper.currency;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.dto.currency.CurrencyResponseDTO;
import org.example.currency_exchange.entity.Currency;

public class CurrencyMapper implements ObjectDtoMapper<Currency, CurrencyResponseDTO> {

    @Override
    public CurrencyResponseDTO objectToDto(Currency currency) {
        return new CurrencyResponseDTO(currency.id(), currency.name(), currency.code(), currency.sign());
    }

    @Override
    public Currency dtoToObject(CurrencyResponseDTO currencyResponseDTO) {
        return null;
    }
}
