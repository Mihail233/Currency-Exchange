package org.example.currency_exchange.dto.exchange;

import lombok.NonNull;
import org.example.currency_exchange.entity.Currency;

import java.math.BigDecimal;

public record ExchangeResponseDTO(@NonNull Currency baseCurrency, @NonNull Currency targetCurrency, @NonNull BigDecimal rate, @NonNull BigDecimal amount, @NonNull BigDecimal convertedAmount) {
}
