package org.example.currency_exchange.exchange_rate.servlet.exchange_rates_servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.common.BaseHttpServlet;
import org.example.currency_exchange.common.ExceptionHandler;
import org.example.currency_exchange.exception.RequiredFormFieldMissException;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateAdditionDTO;
import org.example.currency_exchange.exchange_rate.dto.ExchangeRateDTO;
import org.example.currency_exchange.exchange_rate.service.ExchangeRateService;
import org.example.currency_exchange.util.ServletUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends BaseHttpServlet {
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private final ExceptionHandler exceptionHandler = new ExceptionHandler(ExchangeRatesTypeException.values());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<ExchangeRateDTO> exchangeRateDTOs = exchangeRateService.getExchangeRates();
            sendSuccessfulResponse(HttpServletResponse.SC_OK, exchangeRateDTOs, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<String, String> parameters = ServletUtil.getParametersFromBody(request);
            ExchangeRateAdditionDTO exchangeRateAdditionDTO = convertMapToDto(parameters);
            ExchangeRateDTO exchangeRateDTO = exchangeRateService.addExchangeRate(exchangeRateAdditionDTO);
            sendSuccessfulResponse(HttpServletResponse.SC_CREATED, exchangeRateDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }


    private ExchangeRateAdditionDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            return ServletUtil.getJsonConverter().getMapper().convertValue(parameters, ExchangeRateAdditionDTO.class);
        } catch (RuntimeException e) {
            throw new RequiredFormFieldMissException("Отсутсвтвует нужное поле формы");
        }
    }
}

