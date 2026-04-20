package org.example.currency_exchange.servlet.currency;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.entity.Currency;
import org.example.currency_exchange.dto.currency.CurrencyRequestDTO;
import org.example.currency_exchange.dto.currency.CurrencyResponseDTO;
import org.example.currency_exchange.service.currency.CurrencyService;
import org.example.currency_exchange.exception.RequiredFormFieldMissException;
import org.example.currency_exchange.util.ServletUtil;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    private CurrencyService currencyService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.currencyService = (CurrencyService) getServletContext().getAttribute("currencyService");

        if (currencyService == null) {
            throw new IllegalStateException("currencyService не найден");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CurrencyResponseDTO> currencyResponseDTOs = currencyService.getCurrencies();
        ServletUtil.sendResponse(HttpServletResponse.SC_OK, currencyResponseDTOs, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> parameters = ServletUtil.getParametersFromBody(request);
        CurrencyRequestDTO currencyRequestDTO = convertMapToDto(parameters);
        CurrencyResponseDTO currencyResponseDTO = currencyService.addCurrency(currencyRequestDTO);
        ServletUtil.sendResponse(HttpServletResponse.SC_CREATED, currencyResponseDTO, response);
    }

    //кастомный под каждый сервлет?
    private CurrencyRequestDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            CurrencyRequestDTO currencyRequestDTO = ServletUtil.getJsonConverter().getMapper().convertValue(parameters, CurrencyRequestDTO.class);
            checkCurrencySign(currencyRequestDTO);
            return currencyRequestDTO;
        } catch (RuntimeException e) {
            //порядок не решает, illegalArgumentException - если не те аргументы были переданы
            throw new RequiredFormFieldMissException("A required form field is missing");
        }
    }

    private void checkCurrencySign(CurrencyRequestDTO currencyRequestDTO) {
        if (currencyRequestDTO.sign().length() > Currency.MAX_SIGN_SIZE) {
            throw new RuntimeException();
        }
    }
}

