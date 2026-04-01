package org.example.currency_exchange.exchange_rates.mapper;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;

public class ExchangeRateMapper implements ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> {
    private final static ObjectDtoMapper<Currency, CurrencyDTO> OBJECT_DTO_MAPPER = new CurrencyMapper();

    @Override
    public ExchangeRateDTO objectToDto(ExchangeRate exchangeRate) {
        CurrencyDTO baseCurrencyDTO = OBJECT_DTO_MAPPER.objectToDto(exchangeRate.getBaseCurrency());
        CurrencyDTO targetCurrencyDTO = OBJECT_DTO_MAPPER.objectToDto(exchangeRate.getTargetCurrency());
        return new ExchangeRateDTO(exchangeRate.getId(), baseCurrencyDTO, targetCurrencyDTO, exchangeRate.getRate());
    }

    @Override
    public ExchangeRate dtoToObject(ExchangeRateDTO DTO) {
        return null;
    }
}