package org.example.currency_exchange.currency.servlet;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.List;
import org.example.currency_exchange.commons.BaseServlet;
import org.example.currency_exchange.commons.JsonConverter;
import org.example.currency_exchange.currency.CurrenciesJsonConverter;
import org.example.currency_exchange.currency.CurrencyDTO;
import org.example.currency_exchange.exception_and_error.DataBaseUnavailableException;
import org.example.currency_exchange.currency.service.CurrencyService;

//вызов сервиса
//отдача ответа
//проброс ошибки -> переход в статус запроса
//message - json представление DTO объекта

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet implements BaseServlet {

    private final CurrencyService CurrencyService = new CurrencyService();
    private final JsonConverter<List<CurrencyDTO>> JsonConverter = new CurrenciesJsonConverter();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        generateResponse(response);
    }

    @Override
    public void generateResponse(HttpServletResponse response) throws IOException {
        //перекинуть это в блок validator
        int statusCode;
        try {
            List<CurrencyDTO> currencyDTOS = CurrencyService.getCurrencies();
            statusCode = HttpServletResponse.SC_OK;

            String json = JsonConverter.convertToJSON(currencyDTOS);
            sendResponse(statusCode, json, response);
        } catch (DataBaseUnavailableException e) {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

            String jsonError = JsonConverter.getJsonError(statusCode);
            sendResponse(statusCode, jsonError, response);
        }
        // DTO -> json, json -> message
    }

    @Override
    public void sendResponse(int code, String message, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        response.setStatus(code);
        printWriter.println(message);
    }
}

