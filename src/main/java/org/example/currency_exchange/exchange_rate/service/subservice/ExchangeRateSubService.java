package org.example.currency_exchange.exchange_rate.service.subservice;

import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.common.dao.CurrencyDAO;
import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.exception.CurrencyNotFoundException;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rate.ExchangeRate;
import org.example.currency_exchange.exchange_rate.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateUpdateDTO;
import org.example.currency_exchange.exchange_rate.mapper.CurrencyPairMapper;
import org.example.currency_exchange.exchange_rate.mapper.ExchangeRateMapper;

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
        int size = Currency.CODE_SIZE;
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
