package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.exception_and_error.*;
import org.example.currency_exchange.exchange_rates.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO<T> {
    public List<T> findExchangeRates() throws DataBaseUnavailableException;
    public T findExchangeRateByCurrencyPair(String baseCurrency, String targetCurrency) throws DataBaseUnavailableException, ExchangeRateNotFoundException;
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyPairWithThisCodeAlreadyExists;
    //поменять???
    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException;
}
