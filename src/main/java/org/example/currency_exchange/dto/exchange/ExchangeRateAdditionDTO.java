package org.example.currency_exchange.dto.exchange;

import lombok.NonNull;

import java.math.BigDecimal;

public record ExchangeRateAdditionDTO(@NonNull String baseCurrencyCode,@NonNull String targetCurrencyCode,@NonNull BigDecimal rate) {
}
