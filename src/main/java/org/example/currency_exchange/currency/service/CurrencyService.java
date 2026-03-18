package org.example.currency_exchange.currency.service;


import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.JdbcSqliteCurrencyDAO;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.service.subservice.CurrenciesSubService;
import org.example.currency_exchange.currency.service.subservice.CurrencySubService;


import java.util.List;

//паттерн стратегия
public class CurrencyService {
    private final CurrenciesSubService currenciesSubService;
    private final CurrencySubService currencySubService;

    public CurrencyService() {
        CurrencyDAO<Currency> currencyDAO = new JdbcSqliteCurrencyDAO();
        currenciesSubService = new CurrenciesSubService(currencyDAO);
        currencySubService = new CurrencySubService(currencyDAO);
    }

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        return currenciesSubService.getCurrencies();
    }

    public CurrencyDTO getCurrency(CodeDTO codeDTO) throws DataBaseUnavailableException, CurrencyNotFoundException {
        return currencySubService.getCurrency(codeDTO);
    }

    public void setCurrency(CurrencyAdditionDTO currencyAdditionDTO) throws DataBaseUnavailableException {
        currenciesSubService.setCurrency(currencyAdditionDTO);
    }
}