package org.example.currency_exchange.exchange.dto;

import org.example.currency_exchange.Currency;

public record ExchangeDTO(Currency baseCurrency, Currency targetCurrency, String rate, String amount, String convertedAmount) {
}
