package org.example.currency_exchange.mapper.exchange;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.dto.exchange.ExchangeRateResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesMapper implements ObjectDtoMapper<List<ExchangeRate>, List<ExchangeRateResponseDTO>> {
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateResponseDTO> mapper = new ExchangeRateMapper();

    @Override
    public List<ExchangeRateResponseDTO> objectToDto(List<ExchangeRate> exchangeRates) {
        List<ExchangeRateResponseDTO> exchangeRateResponseDTOs = new ArrayList<>();
        for (ExchangeRate exchangeRate : exchangeRates) {
            ExchangeRateResponseDTO exchangeRateResponseDTO = mapper.objectToDto(exchangeRate);
            exchangeRateResponseDTOs.add(exchangeRateResponseDTO);
        }
        return exchangeRateResponseDTOs;
    }

    @Override
    public List<ExchangeRate> dtoToObject(List<ExchangeRateResponseDTO> exchangeRateResponseDTOs) {
        return null;
    }
}
