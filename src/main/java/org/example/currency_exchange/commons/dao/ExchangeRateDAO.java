package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rates.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO<T> {
    public List<T> findExchangeRates() throws DataBaseUnavailableException;
    public T findExchangeRateByCurrencyPair(String fromCurrency, String toCurrency) throws DataBaseUnavailableException, ExchangeRateNotFoundException;
    //поменять
    public ExchangeRate saveExchangeRate(Currency currency) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException;
    public void updateExchangeRate();
}
