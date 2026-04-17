package org.example.currency_exchange.exchange.service;

import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange.dto.ExchangeDTO;
import org.example.currency_exchange.exchange.dto.ExchangeRequestDTO;
import org.example.currency_exchange.exchange.service.subservice.ExchangeSubService;
import org.example.currency_exchange.exchange_rate.ExchangeRate;
import org.example.currency_exchange.exchange_rate.JdbcSqliteExchangeRateDAO;

public class ExchangeService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO = new JdbcSqliteExchangeRateDAO();
    private final ExchangeSubService exchangeSubService = new ExchangeSubService(exchangeRateDAO);

    public ExchangeDTO makeExchange(ExchangeRequestDTO exchangeRequestDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        return exchangeSubService.makeExchange(exchangeRequestDTO);
    }
}
