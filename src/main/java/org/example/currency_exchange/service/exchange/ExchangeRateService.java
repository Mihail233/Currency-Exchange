package org.example.currency_exchange.service.exchange;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.CurrencyPairWithThisCodeAlreadyExists;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.entity.ExchangeRate;
import org.example.currency_exchange.dto.exchange.ExchangeRateRequestDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRateResponseDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRateUpdateRequestDTO;
import org.example.currency_exchange.mapper.exchange.ExchangeRateMapper;
import org.example.currency_exchange.mapper.exchange.ExchangeRatesMapper;

import java.util.List;

public class ExchangeRateService {
    private final ObjectDtoMapper<List<ExchangeRate>, List<ExchangeRateResponseDTO>> exchangeRatesMapper = new ExchangeRatesMapper();
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateResponseDTO> exchangeRateMapper = new ExchangeRateMapper();
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;

    public ExchangeRateService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public List<ExchangeRateResponseDTO> getExchangeRates() throws DataBaseUnavailableException {
        List<ExchangeRate> exchangeRates = exchangeRateDAO.findExchangeRates();
        return exchangeRatesMapper.objectToDto(exchangeRates);
    }

    public ExchangeRateResponseDTO getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        ExchangeRate exchangeRate = exchangeRateDAO.findExchangeRateByCurrencyPair(baseCurrencyCode, targetCurrencyCode);
        return exchangeRateMapper.objectToDto(exchangeRate);
    }

    public ExchangeRateResponseDTO addExchangeRate(ExchangeRateRequestDTO exchangeRateRequestDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, CurrencyPairWithThisCodeAlreadyExists {
        ExchangeRate exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRateRequestDTO.baseCurrencyCode(), exchangeRateRequestDTO.targetCurrencyCode(), exchangeRateRequestDTO.rate());
        return exchangeRateMapper.objectToDto(exchangeRate);
    }

    public ExchangeRateResponseDTO updateExchangeRate(ExchangeRateUpdateRequestDTO exchangeRateUpdateRequestDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException {
        ExchangeRate exchangeRate = exchangeRateDAO.updateExchangeRate(exchangeRateUpdateRequestDTO.baseCurrencyCode(), exchangeRateUpdateRequestDTO.targetCurrencyCode(), exchangeRateUpdateRequestDTO.rate());
        return exchangeRateMapper.objectToDto(exchangeRate);
    }
}
