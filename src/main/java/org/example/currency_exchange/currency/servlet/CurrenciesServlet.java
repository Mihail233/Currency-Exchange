package org.example.currency_exchange.currency.servlet;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.List;
import org.example.currency_exchange.commons.BaseServlet;
import org.example.currency_exchange.ErrorEntity;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.currency.CurrencyDTO;
import org.example.currency_exchange.currency.ErrorHandler;
import org.example.currency_exchange.currency.service.CurrencyService;

//вызов сервиса
//отдача ответа
//проброс ошибки -> переход в статус запроса
//message - json представление DTO объекта

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet implements BaseServlet {
    private static final String PATH_TO_ERROR_MESSAGES = "apiErrorCodes/currencyError.property";
    private final CurrencyService CurrencyService = new CurrencyService();
    private final JsonConverter JsonConverter = new JsonConverter();
    private final ErrorHandler errorHandler = new ErrorHandler(PATH_TO_ERROR_MESSAGES);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateResponse(response);
    }

    @Override
    public void generateResponse(HttpServletResponse response) throws IOException {
        // DTO -> json, json -> message
        try {
            List<CurrencyDTO> currencyDTOS = CurrencyService.getCurrencies();
            int statusCode = HttpServletResponse.SC_OK;
            String json = JsonConverter.convertToJSON(currencyDTOS);
            sendResponse(statusCode, json, response);

        } catch (IOException e) {
            ErrorEntity errorEntity = errorHandler.catchError(e);
            int statusCode = errorEntity.getStatusCode();
            String json = JsonConverter.convertToJSON(errorEntity);
            sendResponse(statusCode, json, response);
        }
    }

    @Override
    public void sendResponse(int code, String message, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        response.setStatus(code);
        printWriter.println(message);
    }
}

