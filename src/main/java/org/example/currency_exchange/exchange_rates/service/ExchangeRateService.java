package org.example.currency_exchange.exchange_rates.service;

import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.JdbcSqliteExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.service.subservice.ExchangeRateSubService;
import org.example.currency_exchange.exchange_rates.service.subservice.ExchangeRatesSubService;

import java.util.List;

public class ExchangeRateService {
    public final static int CODE_SIZE = 3;
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRate();
    private final ExchangeRatesSubService exchangeRatesSubService = new ExchangeRatesSubService(exchangeRateDAO);
    private final ExchangeRateSubService exchangeRateSubService = new ExchangeRateSubService(exchangeRateDAO);

    public List<ExchangeRateDTO> getExchangeRates() throws DataBaseUnavailableException {
        return exchangeRatesSubService.getExchangeRates();
    }

    public ExchangeRateDTO getExchangeRate(CurrencyPairDTO currencyPairDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        return exchangeRateSubService.getExchangeRate(currencyPairDTO);
    }
}
