package org.example.currency_exchange.exchange_rate.servlet.exchange_rate_servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.common.BaseHttpServlet;
import org.example.currency_exchange.common.ExceptionHandler;
import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.exception.RequiredFormFieldMissException;
import org.example.currency_exchange.exchange_rate.dto.CurrencyPairDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateUpdateDTO;
import org.example.currency_exchange.exchange_rate.mapper.CurrencyPairMapper;
import org.example.currency_exchange.exchange_rate.service.ExchangeRateService;
import org.example.currency_exchange.util.ServletUtil;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends BaseHttpServlet {
    private final ObjectDtoMapper<String, CurrencyPairDTO> objectDtoMapper = new CurrencyPairMapper();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final ExceptionHandler exceptionHandler = new ExceptionHandler(ExchangeRateTypeException.values());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String currencyPair = ServletUtil.getParameterFromPath(request.getPathInfo());
            CurrencyPairDTO currencyPairDTO = objectDtoMapper.objectToDto(currencyPair);
            ExchangeRateDTO exchangeRateDTO = exchangeRateService.getExchangeRate(currencyPairDTO);
            sendSuccessfulResponse(HttpServletResponse.SC_OK, exchangeRateDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String currencyPair = ServletUtil.getParameterFromPath(request.getPathInfo());
            Map<String, String> parameters = ServletUtil.getParametersFromBody(request);
            addCurrencyPairInParameters(currencyPair, parameters);
            ExchangeRateUpdateDTO exchangeRateUpdateDTO = convertMapToDto(parameters);

            ExchangeRateDTO exchangeRateDTO = exchangeRateService.updateExchangeRate(exchangeRateUpdateDTO);
            sendSuccessfulResponse(HttpServletResponse.SC_OK, exchangeRateDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    private ExchangeRateUpdateDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            return ServletUtil.getJsonConverter().getMapper().convertValue(parameters, ExchangeRateUpdateDTO.class);
        } catch (RuntimeException e) {
            throw new RequiredFormFieldMissException("Отсутсвтвует нужное поле формы");
        }
    }

    private void addCurrencyPairInParameters(String currencyPair, Map<String, String> parameters) {
        parameters.put("currencyPair", currencyPair);
    }
}
