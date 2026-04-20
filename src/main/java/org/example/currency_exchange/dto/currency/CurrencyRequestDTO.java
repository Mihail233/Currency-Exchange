package org.example.currency_exchange.dto.currency;

import lombok.NonNull;

public record CurrencyRequestDTO(@NonNull String name, @NonNull String code, @NonNull String sign) {
}
