package org.example.currency_exchange.mapper.exchange;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.dto.currency.CurrencyResponseDTO;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.dto.exchange.ExchangeRateResponseDTO;
import org.example.currency_exchange.mapper.currency.CurrencyMapper;

public class ExchangeRateMapper implements ObjectDtoMapper<ExchangeRate, ExchangeRateResponseDTO> {
    private final CurrencyMapper currencyMapper = new CurrencyMapper();

    @Override
    public ExchangeRateResponseDTO objectToDto(ExchangeRate exchangeRate) {
        CurrencyResponseDTO baseCurrencyResponseDTO = currencyMapper.objectToDto(exchangeRate.baseCurrency());
        CurrencyResponseDTO targetCurrencyResponseDTO = currencyMapper.objectToDto(exchangeRate.targetCurrency());

        return new ExchangeRateResponseDTO(exchangeRate.id(), baseCurrencyResponseDTO, targetCurrencyResponseDTO, exchangeRate.rate());
    }

    @Override
    public ExchangeRate dtoToObject(ExchangeRateResponseDTO DTO) {
        return null;
    }
}