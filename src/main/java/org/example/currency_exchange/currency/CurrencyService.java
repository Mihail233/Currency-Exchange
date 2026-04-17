package org.example.currency_exchange.currency;


import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CurrencyAdditionMapper;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;

import java.util.ArrayList;
import java.util.List;

public class CurrencyService {
    private final CurrencyDAO<Currency> currencyDAO;
    private final ObjectDtoMapper<Currency, CurrencyDTO> objectDtoMapper = new CurrencyMapper();
    private final ObjectDtoMapper<Currency, CurrencyAdditionDTO> currencyAdditionMapper = new CurrencyAdditionMapper();

    public CurrencyService(CurrencyDAO<Currency> currencyDAO ) {
        this.currencyDAO = currencyDAO;
    }

    public List<CurrencyDTO> getCurrencies() throws DataBaseUnavailableException {
        List<Currency> currencies = currencyDAO.findCurrencies();
        return convertCurrenciesToDTO(currencies);
    }

    public CurrencyDTO getCurrency(CodeDTO codeDTO) throws DataBaseUnavailableException, CurrencyNotFoundException {
        String currencyCode = codeDTO.code();
        Currency currency = currencyDAO.findCurrencyByCode(currencyCode);
        return objectDtoMapper.objectToDto(currency);
    }

    public CurrencyDTO addCurrency(CurrencyAdditionDTO currencyAdditionDTO) throws DataBaseUnavailableException, CurrencyWithThisCodeExistsException {
        Currency currencyAddition = currencyAdditionMapper.dtoToObject(currencyAdditionDTO);
        Currency currency = currencyDAO.saveCurrency(currencyAddition);
        return objectDtoMapper.objectToDto(currency);
    }

    private List<CurrencyDTO> convertCurrenciesToDTO(List<Currency> currencies) {
        List<CurrencyDTO> currencyDTOs = new ArrayList<>();
        for (Currency currency: currencies) {
            CurrencyDTO currencyDTO = objectDtoMapper.objectToDto(currency);
            currencyDTOs.add(currencyDTO);
        }
        return currencyDTOs;
    }
}