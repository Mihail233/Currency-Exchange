package org.example.currency_exchange.exchange.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.common.BaseHttpServlet;
import org.example.currency_exchange.common.ExceptionHandler;
import org.example.currency_exchange.exception.RequiredQueryParametersMissException;
import org.example.currency_exchange.exchange.dto.ExchangeDTO;
import org.example.currency_exchange.exchange.dto.ExchangeRequestDTO;
import org.example.currency_exchange.exchange.service.ExchangeService;
import org.example.currency_exchange.util.ServletUtil;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ExchangeServlet", value = "/exchange")
public class ExchangeServlet extends BaseHttpServlet {
    private final ExchangeService exchangeService = new ExchangeService();
    private final ExceptionHandler exceptionHandler = new ExceptionHandler(ExchangeTypeException.values());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<String, String> queryParameters = ServletUtil.getParametersFromQueryParameters(request.getQueryString());
            ExchangeRequestDTO exchangeRequestDTO = convertMapToDto(queryParameters);
            ExchangeDTO exchangeDTO = exchangeService.makeExchange(exchangeRequestDTO);
            sendSuccessfulResponse(HttpServletResponse.SC_OK, exchangeDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    private ExchangeRequestDTO convertMapToDto(Map<String, String> queryParameters) throws RequiredQueryParametersMissException {
        try {
            return ServletUtil.getJsonConverter().getMapper().convertValue(queryParameters, ExchangeRequestDTO.class);
        } catch (RuntimeException e) {
            throw new RequiredQueryParametersMissException("Отсутствует нужное query параметр в пути");
        }
    }
}
