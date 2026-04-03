package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.exception_and_error.*;
import org.example.currency_exchange.exchange_rates.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO<T> {
    public List<T> findExchangeRates() throws DataBaseUnavailableException;
    public T findExchangeRateByCurrencyPair(String base, String target) throws DataBaseUnavailableException, ExchangeRateNotFoundException;
    //поменять
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyPairWithThisCodeAlreadyExists;
    public void updateExchangeRate();
}
