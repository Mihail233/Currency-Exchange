package org.example.currency_exchange.currency.service.subservice;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.AdditionCurrencyMapper;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesSubService {
    private final CurrencyDAO<Currency> currencyDAO;
    private final ObjectDtoMapper<Currency, CurrencyDTO> currencyMapper = new CurrencyMapper();
    private final AdditionCurrencyMapper additionCurrencyMapper = new AdditionCurrencyMapper();

    public CurrenciesSubService(CurrencyDAO<Currency> currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = currencyDAO.findCurrencies();
        return convertCurrenciesToDTO(currencies);
    }

    private List<CurrencyDTO> convertCurrenciesToDTO(List<Currency> currencies) {
        List<CurrencyDTO> currencyDTOs = new ArrayList<>();
        for (Currency currency: currencies) {
            CurrencyDTO currencyDTO = currencyMapper.objectToDto(currency);
            currencyDTOs.add(currencyDTO);
        }
        return currencyDTOs;
    }

    public CurrencyDTO setCurrency(CurrencyAdditionDTO currencyAdditionDTO) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        Currency currencyAddition = additionCurrencyMapper.dtoToObject(currencyAdditionDTO);
        Currency currency = currencyDAO.saveCurrency(currencyAddition);
        return currencyMapper.objectToDto(currency);
    }
}
