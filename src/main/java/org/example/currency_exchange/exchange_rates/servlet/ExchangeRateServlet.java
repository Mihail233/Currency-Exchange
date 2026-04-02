package org.example.currency_exchange.exchange_rates.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.BaseHttpServlet;
import org.example.currency_exchange.commons.ExceptionHandler;
import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.util.ServletUtil;
import org.example.currency_exchange.exchange_rates.ExchangeRateHandler;
import org.example.currency_exchange.exchange_rates.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rates.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rates.mapper.CurrencyPairMapper;
import org.example.currency_exchange.exchange_rates.service.ExchangeRateService;

import java.io.IOException;

@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends BaseHttpServlet {
    private final ObjectDtoMapper<String, CurrencyPairDTO> objectDtoMapper = new CurrencyPairMapper();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final ExceptionHandler exceptionHandler = new ExchangeRateHandler();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String currencyPair = ServletUtil.getParameterFromPath(request.getPathInfo());
            CurrencyPairDTO currencyPairDTO = objectDtoMapper.objectToDto(currencyPair);
            ExchangeRateDTO exchangeRateDTO = exchangeRateService.getExchangeRate(currencyPairDTO);
            sendSuccessfulResponse(exchangeRateDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }
}
