package org.example.currency_exchange.commons.dao;

import org.example.currency_exchange.exception_and_error.*;
import org.example.currency_exchange.exchange_rates.ExchangeRate;

import java.util.List;

public interface ExchangeRateDAO<T> {
    public List<T> findExchangeRates() throws DataBaseUnavailableException;
    public T findExchangeRateByCurrencyPair(String baseCurrencyCode, String targetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException;
    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyPairWithThisCodeAlreadyExists;
    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException;
    public List<ExchangeRate> findIndirectExchangeRate(String firstTargetCurrencyCode, String secondTargetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException;
}
