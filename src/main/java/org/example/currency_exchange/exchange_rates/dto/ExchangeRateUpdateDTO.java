package org.example.currency_exchange.exchange_rates.dto;

import lombok.NonNull;

public record ExchangeRateUpdateDTO(@NonNull String currencyPair, @NonNull String rate) {
}
