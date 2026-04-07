package org.example.currency_exchange.exchange.service.subservice;

import org.example.currency_exchange.Currency;
import org.example.currency_exchange.commons.dao.ExchangeRateDAO;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.exception_and_error.ExchangeRateNotFoundException;
import org.example.currency_exchange.exchange.dto.ExchangeDTO;
import org.example.currency_exchange.exchange.dto.ExchangeRequestDTO;
import org.example.currency_exchange.exchange_rates.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ExchangeSubService {
    private final ExchangeRateDAO<ExchangeRate> exchangeRateDAO;

    public ExchangeSubService(ExchangeRateDAO<ExchangeRate> exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public ExchangeDTO makeExchange(ExchangeRequestDTO exchangeRequestDTO) throws DataBaseUnavailableException, ExchangeRateNotFoundException {
        String baseCurrencyCode = exchangeRequestDTO.from();
        String targetCurrencyCode = exchangeRequestDTO.to();

        BigDecimal amount = new BigDecimal(exchangeRequestDTO.amount());
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
        String rate = exchangeRate.getRate();
        String convertedAmount = convertAmount(amount, new BigDecimal(rate));

        return constructExchangeDTO(exchangeRate.getBaseCurrency(), exchangeRate.getTargetCurrency(), exchangeRate.getRate(), amount, convertedAmount);
    }

    private ExchangeDTO makeReverseExchange(BigDecimal amount, ExchangeRate exchangeRate) {
        BigDecimal rate = new BigDecimal(exchangeRate.getRate());
        BigDecimal reverseRate = getReverseRate(rate);
        String convertedAmount = convertAmount(amount, reverseRate);

        return constructExchangeDTO(exchangeRate.getTargetCurrency(), exchangeRate.getBaseCurrency(), reverseRate.toPlainString(), amount, convertedAmount);
    }

    private ExchangeDTO makeIndirectExchange(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount, List<ExchangeRate> exchangeRates) {
        ExchangeRate fromExchangeRate = getFromOrToExchangeRate(baseCurrencyCode, exchangeRates);
        ExchangeRate toExchangeRate = getFromOrToExchangeRate(targetCurrencyCode, exchangeRates);
        String fromRate = fromExchangeRate.getRate();
        String toRate = toExchangeRate.getRate();

        BigDecimal indirectRate = divideNumeratorByDenominator(new BigDecimal(fromRate), new BigDecimal(toRate));
        String convertedAmount = convertAmount(amount, indirectRate);

        return constructExchangeDTO(fromExchangeRate.getTargetCurrency(), toExchangeRate.getTargetCurrency(), indirectRate.toPlainString(), amount, convertedAmount);
    }

    //0.0001 * 10 -> 0.01; 0.001 * 10 -> 0.01
    private String convertAmount(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = amount.multiply(rate);
        return convertedAmount.setScale(ExchangeRate.SCALE, ExchangeRate.ROUNDING_MODE).stripTrailingZeros().toPlainString();
    }

    private BigDecimal getReverseRate(BigDecimal rate) {
        BigDecimal forReverseRate = new BigDecimal(1);
        return divideNumeratorByDenominator(forReverseRate, rate);
    }

    private BigDecimal divideNumeratorByDenominator(BigDecimal numerator, BigDecimal denominator) {
        return numerator.divide(denominator, ExchangeRate.SCALE, RoundingMode.CEILING);
    }

    private ExchangeRate getFromOrToExchangeRate(String currencyCode, List<ExchangeRate> exchangeRates) {
        return exchangeRates.stream()
                .filter(exchangeRate -> !exchangeRate.getTargetCurrency().getCode().equals(currencyCode))
                .findFirst()
                .get();
    }

    private ExchangeDTO constructExchangeDTO(Currency baseCurrency, Currency targetCurrency, String rate, BigDecimal amount, String convertedAmount) {
        return new ExchangeDTO(baseCurrency, targetCurrency, rate, amount.toPlainString(), convertedAmount);
    }
}
