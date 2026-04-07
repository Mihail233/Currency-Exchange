package org.example.currency_exchange.exchange_rates;

import org.example.currency_exchange.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeRate {
    public final static int SCALE = 2;
    public final static RoundingMode ROUNDING_MODE = RoundingMode.CEILING;
    private Integer id;
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private final String rate;

    public ExchangeRate(Integer id, Currency baseCurrency, Currency targetCurrency, String rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = new BigDecimal(rate).setScale(SCALE, ROUNDING_MODE).stripTrailingZeros().toPlainString();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public String getRate() {
        return rate;
    }

    public int getId() {
        return id;
    }
}
