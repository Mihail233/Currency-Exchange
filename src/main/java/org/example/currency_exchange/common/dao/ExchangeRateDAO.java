package org.example.currency_exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeRateDAO<T> {
    public List<T> findExchangeRates();

    public T findExchangeRateByCurrencyPair(String baseCurrencyCode, String targetCurrencyCode);

    public T saveExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate);

    public T updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate);

    public List<T> findIndirectExchangeRate(String firstTargetCurrencyCode, String secondTargetCurrencyCode);
}
