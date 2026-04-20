package org.example.currency_exchange.dto.currency;

import lombok.NonNull;

public record CurrencyDTO(int id, @NonNull String name, @NonNull String code, @NonNull String sign) {
}
