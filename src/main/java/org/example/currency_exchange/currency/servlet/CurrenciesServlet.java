package org.example.currency_exchange.currency.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.ResponseEntity;
import org.example.currency_exchange.commons.BaseHttpServlet;
import org.example.currency_exchange.currency.CurrencyExceptionHandler;
import org.example.currency_exchange.currency.CurrencyTypeException;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.service.CurrencyService;
import org.example.currency_exchange.exception_and_error.RequiredFormFieldMissException;
import org.example.currency_exchange.currency.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//вызов сервиса
//отдача ответа
//проброс ошибки -> переход в статус запроса
//message - json представление DTO объекта

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends BaseHttpServlet {
    private final CurrencyService currencyService = new CurrencyService();
    private final CurrencyExceptionHandler currencyExceptionHandler = new CurrencyExceptionHandler();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<CurrencyDTO> currencyDTOS = currencyService.getCurrencies();
            sendSuccessfulResponse(currencyDTOS, response);
        } catch (IOException e) {
            ResponseEntity responseEntity = currencyExceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String body = getBodyFromRequest(request);
            String encodeBody = decodeBody(body);
            convertBodyToMap(encodeBody);
            Map<String, String> parameters = convertBodyToMap(encodeBody);

            CurrencyAdditionDTO currencyAdditionDTO = convertMapToDto(parameters);
            //понять чтобы не занулял
            //порядок не решает, illegalArgumentException - если не те аргументы были переданы
            currencyService.setCurrency(currencyAdditionDTO);
        } catch (IOException e) {
            ResponseEntity responseEntity = currencyExceptionHandler.catchException(e);
            sendResponse(responseEntity.getStatusCode(), responseEntity.getMessage(), response);
        }
    }

    public String decodeBody(String body) {
        return URLDecoder.decode(body, StandardCharsets.UTF_8);
    }

    //кастомный под каждый сервлет?
    public CurrencyAdditionDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            return getJsonConverter().getMapper().convertValue(parameters, CurrencyAdditionDTO.class);
        } catch (RuntimeException e) {
            throw new RequiredFormFieldMissException("Отсутсвтвует нужное поле формы");
        }
    }

    public String getBodyFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String lineSeparator = System.lineSeparator();
        return reader.lines()
                .collect(Collectors.joining(lineSeparator));
    }

    public Map<String, String> convertBodyToMap(String encodeBody) {
        String[] parameters = encodeBody.split("&");
        return Arrays.stream(parameters)
                .map(parameter -> parameter.split("="))
                .map(Arrays::asList)
                .collect(Collectors.toMap(
                        List::getFirst,
                        List::getLast
                ));
    }
    }

