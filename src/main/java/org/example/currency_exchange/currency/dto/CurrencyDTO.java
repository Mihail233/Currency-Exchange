package org.example.currency_exchange.currency.dto;

import lombok.NonNull;

public record CurrencyDTO(int id, @NonNull String name, @NonNull String code, @NonNull String sign) {
}
