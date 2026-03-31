package org.example.currency_exchange.currency.service.subservice;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CurrencyMapper;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;

public class CurrencySubService {
    private final CurrencyDAO<Currency> currencyDAO;

    public CurrencySubService(CurrencyDAO<Currency> currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    public CurrencyDTO getCurrency(CodeDTO codeDTO) throws DataBaseUnavailableException, CurrencyNotFoundException {
        Currency currency = currencyDAO.findByCode(codeDTO.code());
        ObjectDtoMapper<Currency, CurrencyDTO> currencyMapper = new CurrencyMapper();
        return currencyMapper.objectToDto(currency);
    }
}
