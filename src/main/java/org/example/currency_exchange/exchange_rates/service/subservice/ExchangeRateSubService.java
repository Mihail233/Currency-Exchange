package org.example.currency_exchange.exchange_rates.service.subservice;

import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange_rates.ExchangeRate;
import org.example.currency_exchange.exchange_rates.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.mapper.CurrencyPairMapper;
import org.example.currency_exchange.exchange_rates.mapper.ExchangeRateMapper;
import org.example.currency_exchange.exchange_rates.service.ExchangeRateService;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateSubService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;
    private final ObjectDtoMapper<String, CurrencyPairDTO> objectDtoPairMapper = new CurrencyPairMapper();
    private final ObjectDtoMapper<ExchangeRate, ExchangeRateDTO> objectDtoExchangeRateMapper = new ExchangeRateMapper();

    public ExchangeRateSubService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public ExchangeRateDTO getExchangeRate(CurrencyPairDTO currencyPairDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        String currencyPair = objectDtoPairMapper.dtoToObject(currencyPairDTO);
        List<String> codes = splitCurrencyPairIntoCodes(currencyPair);
        ExchangeRate exchangeRate = exchangeRateDAO.findExchangeRateByCurrencyPair(codes.getFirst(), codes.getLast());
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
}
