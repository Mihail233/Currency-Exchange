package org.example.currency_exchange.exchange_rates.service.subservice;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception_and_error.CurrencyWithThisCodeExistsException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.OneOrBothCurrenciesFromPairNotExistInDatabase;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateAdditionDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.mapper.ExchangeRateMapper;
import org.example.currency_exchange.Currency;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesSubService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> objectDtoMapper = new ExchangeRateMapper();

    public ExchangeRatesSubService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
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

    public ExchangeRateDTO addExchangeRate(ExchangeRateAdditionDTO exchangeRateAdditionDTO) throws DataBaseUnavailableException, OneOrBothCurrenciesFromPairNotExistInDatabase, CurrencyPairWithThisCodeAlreadyExists {
        String baseCurrencyCode = exchangeRateAdditionDTO.baseCurrencyCode();
        String targetCurrencyCode = exchangeRateAdditionDTO.targetCurrencyCode();

        ExchangeRate exchangeRate = exchangeRateDAO.saveExchangeRate(baseCurrencyCode, targetCurrencyCode, exchangeRateAdditionDTO.rate());
        return objectDtoMapper.objectToDto(exchangeRate);
    }
}
