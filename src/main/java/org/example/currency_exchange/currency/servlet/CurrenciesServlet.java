package org.example.currency_exchange.currency.servlet;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.List;
import org.example.currency_exchange.commons.BaseServlet;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.ErrorHandler;
import org.example.currency_exchange.currency.service.CurrencyService;

//вызов сервиса
//отдача ответа
//проброс ошибки -> переход в статус запроса
//message - json представление DTO объекта

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet implements BaseServlet {
    private final CurrencyService currencyService = new CurrencyService();
    private final JsonConverter jsonConverter = new JsonConverter();
    private final ErrorHandler errorHandler = new ErrorHandler();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<CurrencyDTO> currencyDTOS = currencyService.getCurrencies();
            int statusCode = HttpServletResponse.SC_OK;
            String json = jsonConverter.convertToJSON(currencyDTOS);
            sendResponse(statusCode, json, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = errorHandler.catchError(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }
}

