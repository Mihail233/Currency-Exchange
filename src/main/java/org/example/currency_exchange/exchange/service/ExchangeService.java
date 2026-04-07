package org.example.currency_exchange.exchange.service;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange.dto.ExchangeDTO;
import org.example.currency_exchange.exchange.dto.ExchangeRequestDTO;
import org.example.currency_exchange.exchange.service.subservice.ExchangeSubService;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.JdbcSqliteExchangeRate;

public class ExchangeService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRate();
    private final ExchangeSubService exchangeSubService = new ExchangeSubService(exchangeRateDAO);

    public ExchangeDTO makeExchange(ExchangeRequestDTO exchangeRequestDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        return exchangeSubService.makeExchange(exchangeRequestDTO);
    }
}
