package org.example.currency_exchange.mapper.exchange;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.dto.exchange.ExchangeRateDTO;

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