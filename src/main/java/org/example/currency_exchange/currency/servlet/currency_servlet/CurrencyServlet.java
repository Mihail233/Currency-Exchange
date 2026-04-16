package org.example.currency_exchange.currency.servlet.currency_servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.common.BaseHttpServlet;
import org.example.currency_exchange.common.ExceptionHandler;
import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.util.ServletUtil;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CodeMapper;
import org.example.currency_exchange.currency.service.CurrencyService;

import java.io.IOException;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends BaseHttpServlet {
    private final ObjectDtoMapper<String, CodeDTO> objectDtoMapper = new CodeMapper();
    private final CurrencyService currencyService = new CurrencyService();
    private final ExceptionHandler exceptionHandler = new ExceptionHandler(CurrencyServletTypeException.values());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String currencyCode = ServletUtil.getParameterFromPath(request.getPathInfo());
            CodeDTO codeDTO = objectDtoMapper.objectToDto(currencyCode);
            CurrencyDTO currencyDTO = currencyService.getCurrency(codeDTO);
            sendSuccessfulResponse(HttpServletResponse.SC_OK, currencyDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }
}
