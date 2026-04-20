package org.example.currency_exchange.common.dao;

import org.example.currency_exchange.entity.Currency;

import java.util.List;


public interface CurrencyDAO<T> {
    public List<T> findCurrencies();

    public T findCurrencyByCode(String currencyCode);

    public T saveCurrency(Currency currency);
}
