package org.example.currency_exchange.mapper.currency;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyDTO;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesMapper implements ObjectDtoMapper<List<Currency>, List<CurrencyDTO>> {
    private final ObjectDtoMapper<Currency, CurrencyDTO> mapper = new CurrencyMapper();

    @Override
    public List<CurrencyDTO> objectToDto(List<Currency> currencies) {
        List<CurrencyDTO> currencyDTOs = new ArrayList<>();
        for (Currency currency: currencies) {
            CurrencyDTO currencyDTO = mapper.objectToDto(currency);
            currencyDTOs.add(currencyDTO);
        }
        return currencyDTOs;
    }

    @Override
    public List<Currency> dtoToObject(List<CurrencyDTO> currencyDTOs) {
        return null;
    }
}
