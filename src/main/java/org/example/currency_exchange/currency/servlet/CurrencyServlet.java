package org.example.currency_exchange.currency.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.JsonConverter;
import org.example.currency_exchange.ErrorEntity;
import org.example.currency_exchange.commons.BaseServlet;
import org.example.currency_exchange.currency.ErrorHandler;
import org.example.currency_exchange.currency.service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet implements BaseServlet {
    private static final String PATH_TO_ERROR_MESSAGES = "apiErrorCodes/currencyError.property";
    private final CurrencyService CurrencyService = new CurrencyService();
    private final JsonConverter JsonConverter = new JsonConverter();
    private final ErrorHandler errorHandler = new ErrorHandler(PATH_TO_ERROR_MESSAGES);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getRequestURI());
        sendResponse(0, "", response);
    }
}
