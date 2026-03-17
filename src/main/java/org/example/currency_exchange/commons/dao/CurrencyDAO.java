package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.util.List;


public interface CurrencyDAO<T> {
    public List<T> findCurrencies() throws DataBaseUnavailableException;
    public T findByCode(String code) throws DataBaseUnavailableException, CurrencyNotFoundException;
    public void save();
}
