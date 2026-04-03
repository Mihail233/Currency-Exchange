package org.example.currency_exchange.exchange_rates.service.subservice;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.OneOrBothCurrenciesFromPairNotExistInDatabase;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateAdditionDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.mapper.ExchangeRateMapper;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesSubService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;
    private final CurrencyDAO<Currency> currencyDAO;
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> objectDtoMapper = new ExchangeRateMapper();

    public ExchangeRatesSubService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO, CurrencyDAO<Currency> currencyDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
        this.currencyDAO = currencyDAO;
    }

    public List<ExchangeRateDTO> getExchangeRates() throws DataBaseUnavailableException {
        List<ExchangeRate> exchangeRates = exchangeRateDAO.findExchangeRates();
        return convertExchangeRatesToDTO(exchangeRates);
    }

    private List<ExchangeRateDTO> convertExchangeRatesToDTO(List<ExchangeRate> exchangeRates) {
        List<ExchangeRateDTO> exchangeRateDTOs = new ArrayList<>();
        for (ExchangeRate exchangeRate : exchangeRates) {
            ExchangeRateDTO exchangeRateDTO = objectDtoMapper.objectToDto(exchangeRate);
            exchangeRateDTOs.add(exchangeRateDTO);
        }
        return exchangeRateDTOs;
    }

    public ExchangeRateDTO addExchangeRate(ExchangeRateAdditionDTO exchangeRateAdditionDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, CurrencyPairWithThisCodeAlreadyExists {
        Currency baseCurrency = currencyDAO.findCurrencyByCode(exchangeRateAdditionDTO.baseCurrencyCode());
        Currency targetCurrency = currencyDAO.findCurrencyByCode(exchangeRateAdditionDTO.targetCurrencyCode());

        ExchangeRate exchangeRateAddition = new ExchangeRate(null, baseCurrency, targetCurrency, exchangeRateAdditionDTO.rate());
        ExchangeRate exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRateAddition);
        return objectDtoMapper.objectToDto(exchangeRate);
    }
}
