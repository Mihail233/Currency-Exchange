package org.example.currency_exchange.exchange.dto;

import org.example.currency_exchange.currency.Currency;

import java.math.BigDecimal;

public record ExchangeDTO(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
}
