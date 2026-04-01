package org.example.currency_exchange.exchange_rates;

import org.example.currency_exchange.currency.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeRate {
    private int id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    public ExchangeRate(int id, Currency baseCurrency, Currency targetCurrency, String rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = new BigDecimal(rate).setScale(6, RoundingMode.CEILING).stripTrailingZeros();
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public int getId() {
        return id;
    }
}
