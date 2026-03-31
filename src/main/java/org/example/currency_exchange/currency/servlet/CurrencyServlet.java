package org.example.currency_exchange.currency.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.BaseHttpServlet;
import org.example.currency_exchange.commons.ObjectDtoMapper;
import org.example.currency_exchange.currency.CurrencyExceptionHandler;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CodeMapper;
import org.example.currency_exchange.currency.service.CurrencyService;
import org.example.currency_exchange.exception_and_error.InvalidCurrencyCodeInPathException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends BaseHttpServlet {
    private final ObjectDtoMapper<String, CodeDTO> objectDtoMapper = new CodeMapper();
    private final CurrencyService currencyService = new CurrencyService();
    private final CurrencyExceptionHandler currencyExceptionHandler = new CurrencyExceptionHandler();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String currencyCode = getCurrencyCodeFromPath(request.getPathInfo());
            CodeDTO codeDTO = objectDtoMapper.objectToDto(currencyCode);
            CurrencyDTO currencyDTO = currencyService.getCurrency(codeDTO);
            sendSuccessfulResponse(currencyDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = currencyExceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    private String getCurrencyCodeFromPath(String path) throws InvalidCurrencyCodeInPathException {
        String pathSeparator = "/";
        List<String> pathComponents = Arrays.asList(path.split(pathSeparator));
        return filterPathComponents(pathComponents);
    }

    private String filterPathComponents(List<String> pathComponents) throws InvalidCurrencyCodeInPathException {
        pathComponents = pathComponents.stream()
                .filter(component -> !component.isEmpty())
                .toList();

        int allowedAmountPathComponents = 1;
        if (pathComponents.size() != allowedAmountPathComponents) {
            throw new InvalidCurrencyCodeInPathException("Не было передано кода валюты");
        }
        return pathComponents.getFirst();
    }
}
