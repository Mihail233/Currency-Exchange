package org.example.currency_exchange.exchange_rate.mapper;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.exchange_rate.dto.CurrencyPairDTO;

public class CurrencyPairMapper implements ObjectDtoMapper<String, CurrencyPairDTO> {

    @Override
    public CurrencyPairDTO objectToDto(String currencyPair) {
        return new CurrencyPairDTO(currencyPair);
    }

    @Override
    public String dtoToObject(CurrencyPairDTO currencyPairDTO) {
        return currencyPairDTO.currencyPair();
    }
}