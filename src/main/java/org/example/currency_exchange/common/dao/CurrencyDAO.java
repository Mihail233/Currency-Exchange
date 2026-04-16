package org.example.currency_exchange.common.dao;

import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;

import java.util.List;


public interface CurrencyDAO<T> {
    public List<T> findCurrencies() throws DataBaseUnavailableException;

    public T findCurrencyByCode(String currencyCode) throws DataBaseUnavailableException, CurrencyNotFoundException;

    public Currency saveCurrency(Currency currency) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException;
}
