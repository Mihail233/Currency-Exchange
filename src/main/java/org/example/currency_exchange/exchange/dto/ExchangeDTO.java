package org.example.currency_exchange.exchange.dto;

import lombok.NonNull;
import org.example.currency_exchange.currency.Currency;

import java.math.BigDecimal;

public record ExchangeDTO(@NonNull Currency baseCurrency, @NonNull Currency targetCurrency, @NonNull BigDecimal rate, @NonNull BigDecimal amount, @NonNull BigDecimal convertedAmount) {
}
