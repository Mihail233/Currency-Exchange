package org.example.currency_exchange.exchange_rates.dto;

import org.example.currency_exchange.currency.Currency;

import java.math.BigDecimal;

public record ExchangeRateDTO(int id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
}
