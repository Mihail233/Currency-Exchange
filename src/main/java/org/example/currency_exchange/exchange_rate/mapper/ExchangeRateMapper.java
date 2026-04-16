package org.example.currency_exchange.exchange_rate.mapper;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.exchange_rate.ExchangeRate;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateDTO;

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