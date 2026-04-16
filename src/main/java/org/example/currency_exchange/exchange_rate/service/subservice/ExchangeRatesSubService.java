package org.example.currency_exchange.exchange_rate.service.subservice;

import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exchange_rate.ExchangeRate;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateAdditionDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rate.mapper.ExchangeRateMapper;

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
        //возникает raceCondition, но при этом если бы вставляли запись и была бы конкретная ошибка вместо SqlException, то можно было это избежать(вставка на индекс-поля, их нет -> возрат ошибки)
        Currency baseCurrency = currencyDAO.findCurrencyByCode(exchangeRateAdditionDTO.baseCurrencyCode());
        Currency targetCurrency = currencyDAO.findCurrencyByCode(exchangeRateAdditionDTO.targetCurrencyCode());

        ExchangeRate exchangeRateAddition = new ExchangeRate(null, baseCurrency, targetCurrency, exchangeRateAdditionDTO.rate());
        ExchangeRate exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRateAddition);
        return objectDtoMapper.objectToDto(exchangeRate);
    }
}
