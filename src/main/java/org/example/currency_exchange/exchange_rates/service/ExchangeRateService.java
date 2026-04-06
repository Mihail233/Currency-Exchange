package org.example.currency_exchange.exchange_rates.service;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.JdbcSqliteExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateAdditionDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateUpdateDTO;
import org.example.currency_exchange.exchange_rates.service.subservice.ExchangeRateSubService;
import org.example.currency_exchange.exchange_rates.service.subservice.ExchangeRatesSubService;

import java.util.List;

public class ExchangeRateService {
    public final static int CODE_SIZE = 3;
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRate();
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
