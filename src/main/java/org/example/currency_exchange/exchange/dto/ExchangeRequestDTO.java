package org.example.currency_exchange.exchange.dto;

import lombok.NonNull;

import java.math.BigDecimal;

public record ExchangeRequestDTO(@NonNull String from, @NonNull String to, @NonNull BigDecimal amount) {
}
