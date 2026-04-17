package org.example.currency_exchange.exchange_rate.service;

import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rate.ExchangeRate;
import org.example.currency_exchange.exchange_rate.JdbcSqliteExchangeRateDAO;
import org.example.currency_exchange.exchange_rate.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateAdditionDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateUpdateDTO;
import org.example.currency_exchange.exchange_rate.service.subservice.ExchangeRateSubService;
import org.example.currency_exchange.exchange_rate.service.subservice.ExchangeRatesSubService;

import java.util.List;

public class ExchangeRateService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRateDAO();
    private final CurrencyDAO<Currency> currencyDAO = new JdbcSqliteCurrencyDAO();
    private final ExchangeRatesSubService exchangeRatesSubService = new ExchangeRatesSubService(exchangeRateDAO, currencyDAO);
    private final ExchangeRateSubService exchangeRateSubService = new ExchangeRateSubService(exchangeRateDAO, currencyDAO);

    public List<ExchangeRateDTO> getExchangeRates() throws DataBaseUnavailableException {
        return exchangeRatesSubService.getExchangeRates();
    }

    public ExchangeRateDTO getExchangeRate(CurrencyPairDTO currencyPairDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        return exchangeRateSubService.getExchangeRate(currencyPairDTO);
    }

    public ExchangeRateDTO addExchangeRate(ExchangeRateAdditionDTO exchangeRateAdditionDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, CurrencyPairWithThisCodeAlreadyExists {
        return exchangeRatesSubService.addExchangeRate(exchangeRateAdditionDTO);
    }

    public ExchangeRateDTO updateExchangeRate(ExchangeRateUpdateDTO exchangeRateUpdateDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException {
        return exchangeRateSubService.updateExchangeRate(exchangeRateUpdateDTO);
    }
}
