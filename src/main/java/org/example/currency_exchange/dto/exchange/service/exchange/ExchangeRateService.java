package org.example.currency_exchange.dto.exchange.service.exchange;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.dto.exchange.ExchangeRateAdditionDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRateDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRateUpdateDTO;
import org.example.currency_exchange.mapper.exchange.ExchangeRateMapper;
import org.example.currency_exchange.mapper.exchange.ExchangeRatesMapper;

import java.util.List;

public class ExchangeRateService {
    private final ObjectDtoMapper<List<ExchangeRate>, List<ExchangeRateDTO>> exchangeRatesMapper = new ExchangeRatesMapper();
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> exchangeRateMapper = new ExchangeRateMapper();
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;

    public ExchangeRateService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public List<ExchangeRateDTO> getExchangeRates() throws DataBaseUnavailableException {
        List<ExchangeRate> exchangeRates = exchangeRateDAO.findExchangeRates();
        return exchangeRatesMapper.objectToDto(exchangeRates);
    }

    public ExchangeRateDTO getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        ExchangeRate exchangeRate = exchangeRateDAO.findExchangeRateByCurrencyPair(baseCurrencyCode, targetCurrencyCode);
        return exchangeRateMapper.objectToDto(exchangeRate);
    }

    public ExchangeRateDTO addExchangeRate(ExchangeRateAdditionDTO exchangeRateAdditionDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, CurrencyPairWithThisCodeAlreadyExists {
        ExchangeRate exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRateAdditionDTO.baseCurrencyCode(), exchangeRateAdditionDTO.targetCurrencyCode(), exchangeRateAdditionDTO.rate());
        return exchangeRateMapper.objectToDto(exchangeRate);
    }

    public ExchangeRateDTO updateExchangeRate(ExchangeRateUpdateDTO exchangeRateUpdateDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException {
        ExchangeRate exchangeRate = exchangeRateDAO.updateExchangeRate(exchangeRateUpdateDTO.baseCurrencyCode(), exchangeRateUpdateDTO.targetCurrencyCode(), exchangeRateUpdateDTO.rate());
        return exchangeRateMapper.objectToDto(exchangeRate);
    }
}
