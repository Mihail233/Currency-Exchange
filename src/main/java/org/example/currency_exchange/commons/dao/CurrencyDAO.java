package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.util.List;


public interface CurrencyDAO<T> {
    public List<T> findCurrencies() throws DataBaseUnavailableException;
    public T findCurrencyByCode(String currencyCode) throws DataBaseUnavailableException, CurrencyNotFoundException;
    public Currency saveCurrency(Currency currency) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException;
}
