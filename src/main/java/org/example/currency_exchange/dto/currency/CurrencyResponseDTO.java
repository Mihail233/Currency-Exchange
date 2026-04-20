package org.example.currency_exchange.dto.currency;

import lombok.NonNull;

public record CurrencyResponseDTO(int id, @NonNull String name, @NonNull String code, @NonNull String sign) {
}
