package org.example.currency_exchange.mapper.currency;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesMapper implements ObjectDtoMapper<List<Currency>, List<CurrencyResponseDTO>> {
    private final ObjectDtoMapper<Currency, CurrencyResponseDTO> mapper = new CurrencyMapper();

    @Override
    public List<CurrencyResponseDTO> objectToDto(List<Currency> currencies) {
        List<CurrencyResponseDTO> currencyResponseDTOs = new ArrayList<>();
        for (Currency currency: currencies) {
            CurrencyResponseDTO currencyResponseDTO = mapper.objectToDto(currency);
            currencyResponseDTOs.add(currencyResponseDTO);
        }
        return currencyResponseDTOs;
    }

    @Override
    public List<Currency> dtoToObject(List<CurrencyResponseDTO> currencyResponseDTOs) {
        return null;
    }
}
