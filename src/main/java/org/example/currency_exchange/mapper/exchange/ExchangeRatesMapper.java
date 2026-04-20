package org.example.currency_exchange.mapper.exchange;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.dto.exchange.ExchangeRateDTO;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesMapper implements ObjectDtoMapper<List<ExchangeRate>, List<ExchangeRateDTO>> {
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> mapper = new ExchangeRateMapper();

    @Override
    public List<ExchangeRateDTO> objectToDto(List<ExchangeRate> exchangeRates) {
        List<ExchangeRateDTO> exchangeRateDTOs = new ArrayList<>();
        for (ExchangeRate exchangeRate : exchangeRates) {
            ExchangeRateDTO exchangeRateDTO = mapper.objectToDto(exchangeRate);
            exchangeRateDTOs.add(exchangeRateDTO);
        }
        return exchangeRateDTOs;
    }

    @Override
    public List<ExchangeRate> dtoToObject(List<ExchangeRateDTO> exchangeRateDTOs) {
        return null;
    }
}
