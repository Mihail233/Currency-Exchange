package org.example.currency_exchange.exchange_rates.dto;

import lombok.NonNull;
import org.example.currency_exchange.currency.Currency;

import java.math.BigDecimal;

public record ExchangeRateDTO(int id, @NonNull Currency baseCurrency, @NonNull Currency targetCurrency, @NonNull BigDecimal rate) {
}
