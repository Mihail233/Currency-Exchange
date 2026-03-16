package org.example.currency_exchange.currency.service;


import org.example.currency_exchange.currency.CurrencyDTO;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.service.subservice.CurrenciesSubService;

import java.util.List;

//паттерн стратегия
public class CurrencyService {
    private final CurrencyDAO<Currency> CurrencyDAO = new JdbcSqliteCurrencyDAO();
    private final CurrenciesSubService CurrenciesSubService;

    public CurrencyService() {
        CurrenciesSubService = new CurrenciesSubService();
    }

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        return CurrenciesSubService.getCurrencies(CurrencyDAO);
    }
}