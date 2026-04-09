package org.example.currency_exchange.exchange_rates.mapper;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;

public class ExchangeRateMapper implements ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> {

    @Override
    public ExchangeRateDTO objectToDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDTO(exchangeRate.getId(), exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), exchangeRate.getRate());
    }

    @Override
    public ExchangeRate dtoToObject(ExchangeRateDTO DTO) {
        return null;
    }
}