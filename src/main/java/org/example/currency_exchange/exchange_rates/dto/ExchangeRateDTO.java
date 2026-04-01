package org.example.currency_exchange.exchange_rates.dto;

import org.example.currency_exchange.currency.dto.CurrencyDTO;

import java.math.BigDecimal;

public record ExchangeRateDTO(int id, CurrencyDTO baseCurrency, CurrencyDTO targetCurrency, BigDecimal rate) {
}
