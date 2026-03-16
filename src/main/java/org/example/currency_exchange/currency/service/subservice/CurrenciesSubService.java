package org.example.currency_exchange.currency.service.subservice;

import org.example.currency_exchange.currency.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesSubService {
    public List<CurrencyDTO> getCurrencies(CurrencyDAO<Currency> CurrencyDAO) throws DataBaseUnavailableException {
        List<Currency> currencies = CurrencyDAO.findCurrencies();
        return convertCurrenciesToDTO(currencies);
    }

    //не нравится что здесь это реализовано
    public List<CurrencyDTO> convertCurrenciesToDTO(List<Currency> currencies) {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        CurrencyMapper currencyMapper = new CurrencyMapper();
        for (Currency currency: currencies) {
            CurrencyDTO currencyDTO = currencyMapper.objectToDto(currency);
            currencyDTOS.add(currencyDTO);
        }
        return currencyDTOS;
    }
}
