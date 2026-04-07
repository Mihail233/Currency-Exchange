package org.example.currency_exchange.exchange.dto;

import lombok.NonNull;

public record ExchangeRequestDTO(@NonNull String from, @NonNull String to, @NonNull String amount) {
}
