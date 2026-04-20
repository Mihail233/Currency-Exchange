package org.example.currency_exchange.dto.exchange.service.currency;


import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyAdditionDTO;
import org.example.currency_exchange.dto.currency.CurrencyDTO;
import org.example.currency_exchange.mapper.currency.CurrenciesMapper;
import org.example.currency_exchange.mapper.currency.CurrencyAdditionMapper;
import org.example.currency_exchange.mapper.currency.CurrencyMapper;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;

import java.util.List;

public class CurrencyService {
    private final ObjectDtoMapper<Currency, CurrencyDTO> currencyMapper = new CurrencyMapper();
    private final ObjectDtoMapper<List<Currency>, List<CurrencyDTO>> currenciesMapper = new CurrenciesMapper();
    private final ObjectDtoMapper<Currency, CurrencyAdditionDTO> currencyAdditionMapper = new CurrencyAdditionMapper();
    private final CurrencyDAO<Currency> currencyDAO;

    public CurrencyService(CurrencyDAO<Currency> currencyDAO ) {
        this.currencyDAO = currencyDAO;
    }

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = currencyDAO.findCurrencies();
        return currenciesMapper.objectToDto(currencies);
    }

    public CurrencyDTO getCurrency(String currencyCode) throws DataBaseUnavailableException, CurrencyNotFoundException {
        Currency currency = currencyDAO.findCurrencyByCode(currencyCode);
        return currencyMapper.objectToDto(currency);
    }

    public CurrencyDTO addCurrency(CurrencyAdditionDTO currencyAdditionDTO) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        Currency currencyAddition = currencyAdditionMapper.dtoToObject(currencyAdditionDTO);
        Currency currency = currencyDAO.saveCurrency(currencyAddition);
        return currencyMapper.objectToDto(currency);
    }
}