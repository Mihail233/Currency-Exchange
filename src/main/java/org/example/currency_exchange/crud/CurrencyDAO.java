package org.example.currency_exchange.crud;

import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.util.List;


public interface CurrencyDAO<T> {
    public List<T> findCurrencies() throws DataBaseUnavailableException;
    public T findByCode(String code);
    public void save();
}
