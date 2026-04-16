package org.example.currency_exchange.exchange_rate.dto;

import lombok.NonNull;

import java.math.BigDecimal;

public record ExchangeRateUpdateDTO(@NonNull String currencyPair, @NonNull BigDecimal rate) {
}
