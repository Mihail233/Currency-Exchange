package org.example.currency_exchange.currency.service.subservice;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesSubService {
    private final CurrencyDAO<Currency> currencyDAO;

    public CurrenciesSubService(CurrencyDAO<Currency> currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = currencyDAO.findCurrencies();
        return convertCurrenciesToDTO(currencies);
    }

    public List<CurrencyDTO> convertCurrenciesToDTO(List<Currency> currencies) {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        ObjectDtoMapper<Currency, CurrencyDTO> currencyMapper = new CurrencyMapper();
        for (Currency currency: currencies) {
            CurrencyDTO currencyDTO = currencyMapper.objectToDto(currency);
            currencyDTOS.add(currencyDTO);
        }
        return currencyDTOS;
    }
}
