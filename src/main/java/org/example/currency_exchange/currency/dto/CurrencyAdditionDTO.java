package org.example.currency_exchange.currency.dto;

import lombok.NonNull;

public record CurrencyAdditionDTO(@NonNull String name, @NonNull String code, @NonNull String sign) {
}
