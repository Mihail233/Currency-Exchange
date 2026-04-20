package org.example.currency_exchange.dto.exchange.service.exchange;

import org.example.currency_exchange.common.dao.ExchangeRateDAO;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.exception.DataBaseUnavailableException;
import org.example.currency_exchange.exception.ExchangeRateNotFoundException;
import org.example.currency_exchange.dto.exchange.ExchangeDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRequestDTO;
import org.example.currency_exchange.entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ExchangeService {
    private final static int CONVERTED_AMOUNT_SCALE = 2;
    private final static int DIVIDE_SCALE = 6;
    private final static RoundingMode ROUNDING_MODE = RoundingMode.DOWN;
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;

    public ExchangeService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public ExchangeDTO makeExchange(ExchangeRequestDTO exchangeRequestDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        String baseCurrencyCode = exchangeRequestDTO.from();
        String targetCurrencyCode = exchangeRequestDTO.to();

        BigDecimal amount = exchangeRequestDTO.amount();
        return makeOneOfExchangeScenarios(baseCurrencyCode, targetCurrencyCode, amount);
    }

    private ExchangeDTO makeOneOfExchangeScenarios(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        try {
            //a -> b
            ExchangeRate exchangeRate = exchangeRateDAO.findExchangeRateByCurrencyPair(baseCurrencyCode, targetCurrencyCode);
            return makeDefaultExchange(amount, exchangeRate);
        } catch (ExchangeRateNotFoundException e) {
            //b -> a
            try {
                ExchangeRate exchangeRate = exchangeRateDAO.findExchangeRateByCurrencyPair(targetCurrencyCode, baseCurrencyCode);
                return makeReverseExchange(amount, exchangeRate);
            } catch (ExchangeRateNotFoundException ex) {
                //USD -> A, USD -> B: A -> B
                List<ExchangeRate> exchangeRates = exchangeRateDAO.findIndirectExchangeRate(baseCurrencyCode, targetCurrencyCode);
                return makeIndirectExchange(baseCurrencyCode, targetCurrencyCode, amount, exchangeRates);
            }
        }
    }

    private ExchangeDTO makeDefaultExchange(BigDecimal amount, ExchangeRate exchangeRate) {
        BigDecimal rate = exchangeRate.getRate();
        BigDecimal convertedAmount = convertAmount(amount, rate);

        return constructExchangeDTO(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), exchangeRate.getRate(), amount, convertedAmount);
    }

    private ExchangeDTO makeReverseExchange(BigDecimal amount, ExchangeRate exchangeRate) {
        BigDecimal rate = exchangeRate.getRate();
        BigDecimal reverseRate = getReverseRate(rate);
        BigDecimal convertedAmount = convertAmount(amount, reverseRate);

        return constructExchangeDTO(exchangeRate.getTargetCurrency(), exchangeRate.getBaseCurrency(), reverseRate, amount, convertedAmount);
    }

    private ExchangeDTO makeIndirectExchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount, List<ExchangeRate> exchangeRates) {
        ExchangeRate fromExchangeRate = getFromOrToExchangeRate(baseCurrencyCode, exchangeRates);
        ExchangeRate toExchangeRate = getFromOrToExchangeRate(targetCurrencyCode, exchangeRates);
        BigDecimal fromRate = fromExchangeRate.getRate();
        BigDecimal toRate = toExchangeRate.getRate();

        BigDecimal indirectRate = divideNumeratorByDenominator(fromRate, toRate);
        BigDecimal convertedAmount = convertAmount(amount, indirectRate);

        return constructExchangeDTO(fromExchangeRate.getTargetCurrency(), toExchangeRate.getTargetCurrency(), indirectRate, amount, convertedAmount);
    }

    //0.0001 * 10 -> 0.01; 0.001 * 10 -> 0.01
    private BigDecimal convertAmount(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = amount.multiply(rate);
        return convertedAmount.setScale(CONVERTED_AMOUNT_SCALE, ROUNDING_MODE);
    }

    private BigDecimal getReverseRate(BigDecimal rate) {
        BigDecimal forReverseRate = new BigDecimal(1);
        return divideNumeratorByDenominator(forReverseRate, rate);
    }

    private BigDecimal divideNumeratorByDenominator(BigDecimal numerator, BigDecimal denominator) {
        return numerator.divide(denominator, DIVIDE_SCALE, ROUNDING_MODE);
    }

    private ExchangeRate getFromOrToExchangeRate(String currencyCode, List<ExchangeRate> exchangeRates) {
        return exchangeRates.stream()
                .filter(exchangeRate -> !exchangeRate.getTargetCurrency().getCode().equals(currencyCode))
                .findFirst()
                .get();
    }

    private ExchangeDTO constructExchangeDTO(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
        return new ExchangeDTO(baseCurrency, targetCurrency, rate, amount, convertedAmount);
    }
}
