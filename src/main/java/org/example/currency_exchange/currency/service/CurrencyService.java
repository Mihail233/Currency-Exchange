package org.example.currency_exchange.currency.service;


import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.service.subservice.CurrenciesSubService;
import org.example.currency_exchange.currency.service.subservice.CurrencySubService;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;

import java.util.List;

//паттерн делегирование
public class CurrencyService {
    private final CurrencyDAO<Currency> currencyDAO = new JdbcSqliteCurrencyDAO();
    private final CurrenciesSubService currenciesSubService = new CurrenciesSubService(currencyDAO);
    private final CurrencySubService currencySubService = new CurrencySubService(currencyDAO);

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        return currenciesSubService.getCurrencies();
    }

    public CurrencyDTO getCurrency(CodeDTO codeDTO) throws DataBaseUnavailableException, CurrencyNotFoundException {
        return currencySubService.getCurrency(codeDTO);
    }

    public CurrencyDTO addCurrency(CurrencyAdditionDTO currencyAdditionDTO) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        return currenciesSubService.addCurrency(currencyAdditionDTO);
    }
}