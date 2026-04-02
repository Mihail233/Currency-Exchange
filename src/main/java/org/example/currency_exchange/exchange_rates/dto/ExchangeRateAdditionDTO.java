package org.example.currency_exchange.exchange_rates.dto;

import lombok.NonNull;

public record ExchangeRateAdditionDTO(@NonNull String baseCurrencyCode,@NonNull String targetCurrencyCode,@NonNull String rate) {
}
