package org.example.currency_exchange.common.dao;

import org.example.currency_exchange.exception.*;
import org.example.currency_exchange.exchange_rate.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO<T> {
    public List<T> findExchangeRates() throws DataBaseUnavailableException;

    public T findExchangeRateByCurrencyPair(String baseCurrencyCode, String targetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException;

    public T saveExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyPairWithThisCodeAlreadyExists;

    public T updateExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException;

    public List<T> findIndirectExchangeRate(String firstTargetCurrencyCode, String secondTargetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException;
}
