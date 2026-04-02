package org.example.currency_exchange.currency.service.subservice;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.Currency;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CurrencyAdditionMapper;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesSubService {
    private final CurrencyDAO<Currency> currencyDAO;
    private final ObjectDtoMapper<Currency, CurrencyDTO> objectDtoMapper = new CurrencyMapper();
    private final ObjectDtoMapper<Currency, CurrencyAdditionDTO> currencyAdditionMapper = new CurrencyAdditionMapper();

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
            CurrencyDTO currencyDTO = objectDtoMapper.objectToDto(currency);
            currencyDTOs.add(currencyDTO);
        }
        return currencyDTOs;
    }

    public CurrencyDTO addCurrency(CurrencyAdditionDTO currencyAdditionDTO) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        Currency currencyAddition = currencyAdditionMapper.dtoToObject(currencyAdditionDTO);
        Currency currency = currencyDAO.saveCurrency(currencyAddition);
        return objectDtoMapper.objectToDto(currency);
    }
}
