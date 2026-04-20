package org.example.currency_exchange.dto.exchange;

import lombok.NonNull;

import java.math.BigDecimal;

public record ExchangeRequestDTO(@NonNull String from, @NonNull String to, @NonNull BigDecimal amount) {
}
