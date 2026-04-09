package org.example.currency_exchange.currency.servlet.currencies_servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.BaseHttpServlet;
import org.example.currency_exchange.commons.ExceptionHandler;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.service.CurrencyService;
import org.example.currency_exchange.exception_and_error.RequiredFormFieldMissException;
import org.example.currency_exchange.util.ServletUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends BaseHttpServlet {
    private final CurrencyService currencyService = new CurrencyService();
    private final ExceptionHandler exceptionHandler = new ExceptionHandler(CurrenciesServletTypeException.values());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<CurrencyDTO> currencyDTOs = currencyService.getCurrencies();
            sendSuccessfulResponse(currencyDTOs, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<String, String> parameters = ServletUtil.getParametersFromBody(request);
            CurrencyAdditionDTO currencyAdditionDTO = convertMapToDto(parameters);
            CurrencyDTO currencyDTO = currencyService.addCurrency(currencyAdditionDTO);
            sendSuccessfulResponse(currencyDTO, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = exceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    //кастомный под каждый сервлет?
    private CurrencyAdditionDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            return ServletUtil.getJsonConverter().getMapper().convertValue(parameters, CurrencyAdditionDTO.class);
        } catch (RuntimeException e) {
            //порядок не решает, illegalArgumentException - если не те аргументы были переданы
            throw new RequiredFormFieldMissException("Отсутсвтвует нужное поле формы");
        }
    }
}

