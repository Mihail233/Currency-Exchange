package org.example.currency_exchange.dto.exchange;

import lombok.NonNull;
import org.example.currency_exchange.dto.currency.CurrencyResponseDTO;
import org.example.currency_exchange.entity.Currency;

import java.math.BigDecimal;

public record ExchangeRateResponseDTO(int id, @NonNull CurrencyResponseDTO baseCurrency, @NonNull CurrencyResponseDTO targetCurrency, @NonNull BigDecimal rate) {
}
