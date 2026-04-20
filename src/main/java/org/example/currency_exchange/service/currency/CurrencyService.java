package org.example.currency_exchange.service.currency;


import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyRequestDTO;
import org.example.currency_exchange.dto.currency.CurrencyResponseDTO;
import org.example.currency_exchange.mapper.currency.CurrenciesMapper;
import org.example.currency_exchange.mapper.currency.CurrencyAdditionMapper;
import org.example.currency_exchange.mapper.currency.CurrencyMapper;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;

import java.util.List;

public class CurrencyService {
    private final ObjectDtoMapper<Currency, CurrencyResponseDTO> currencyMapper = new CurrencyMapper();
    private final ObjectDtoMapper<List<Currency>, List<CurrencyResponseDTO>> currenciesMapper = new CurrenciesMapper();
    private final ObjectDtoMapper<Currency, CurrencyRequestDTO> currencyAdditionMapper = new CurrencyAdditionMapper();
    private final CurrencyDAO<Currency> currencyDAO;

    public CurrencyService(CurrencyDAO<Currency> currencyDAO ) {
        this.currencyDAO = currencyDAO;
    }

    public List<CurrencyResponseDTO> getCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = currencyDAO.findCurrencies();
        return currenciesMapper.objectToDto(currencies);
    }

    public CurrencyResponseDTO getCurrency(String currencyCode) throws DataBaseUnavailableException, CurrencyNotFoundException {
        Currency currency = currencyDAO.findCurrencyByCode(currencyCode);
        return currencyMapper.objectToDto(currency);
    }

    public CurrencyResponseDTO addCurrency(CurrencyRequestDTO currencyRequestDTO) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        Currency currencyAddition = currencyAdditionMapper.dtoToObject(currencyRequestDTO);
        Currency currency = currencyDAO.saveCurrency(currencyAddition);
        return currencyMapper.objectToDto(currency);
    }
}