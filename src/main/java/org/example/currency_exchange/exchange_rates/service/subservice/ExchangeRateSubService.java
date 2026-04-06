package org.example.currency_exchange.exchange_rates.service.subservice;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.commons.dao.CurrencyDAO;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.CurrencyNotFoundException;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateUpdateDTO;
import org.example.currency_exchange.exchange_rates.mapper.CurrencyPairMapper;
import org.example.currency_exchange.exchange_rates.mapper.ExchangeRateMapper;
import org.example.currency_exchange.exchange_rates.service.ExchangeRateService;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateSubService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;
    private final CurrencyDAO<Currency> currencyDAO;
    private final ObjectDtoMapper<String, CurrencyPairDTO> objectDtoPairMapper = new CurrencyPairMapper();
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> objectDtoExchangeRateMapper = new ExchangeRateMapper();

    public ExchangeRateSubService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO, CurrencyDAO<Currency> currencyDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
        this.currencyDAO = currencyDAO;
    }

    public ExchangeRateDTO getExchangeRate(CurrencyPairDTO currencyPairDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        String currencyPair = objectDtoPairMapper.dtoToObject(currencyPairDTO);
        List<String> currencyCodes = splitCurrencyPairIntoCodes(currencyPair);
        ExchangeRate exchangeRate = exchangeRateDAO.findExchangeRateByCurrencyPair(currencyCodes.getFirst(), currencyCodes.getLast());
        return objectDtoExchangeRateMapper.objectToDto(exchangeRate);
    }

    private List<String> splitCurrencyPairIntoCodes(String currencyPair) {
        int size = ExchangeRateService.CODE_SIZE;
        List<String> codes = new ArrayList<>((currencyPair.length() + size - 1) / size);

        for (int start = 0; start < currencyPair.length(); start += size) {
            codes.add(currencyPair.substring(start, Math.min(currencyPair.length(), start + size)));
        }
        return codes;
    }

    public ExchangeRateDTO updateExchangeRate(ExchangeRateUpdateDTO exchangeRateUpdateDTO) throws DataBaseUnavailableException, CurrencyNotFoundException, ExchangeRateNotFoundException {
        String currencyPair = exchangeRateUpdateDTO.currencyPair();
        List<String> currencyCodes = splitCurrencyPairIntoCodes(currencyPair);

        Currency baseCurrency = currencyDAO.findCurrencyByCode(currencyCodes.getFirst());
        Currency targetCurrency = currencyDAO.findCurrencyByCode(currencyCodes.getLast());

        ExchangeRate exchangeRateUpdate = new ExchangeRate(null, baseCurrency, targetCurrency, exchangeRateUpdateDTO.rate());
        ExchangeRate exchangeRate = exchangeRateDAO.updateExchangeRate(exchangeRateUpdate);
        return objectDtoExchangeRateMapper.objectToDto(exchangeRate);
    }
}
