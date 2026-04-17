package org.example.currency_exchange.currency.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.currency.Currency;
import org.example.currency_exchange.currency.dto.CurrencyAdditionDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.CurrencyService;
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
        List<CurrencyDTO> currencyDTOs = currencyService.getCurrencies();
        ServletUtil.sendResponse(HttpServletResponse.SC_OK, currencyDTOs, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> parameters = ServletUtil.getParametersFromBody(request);
        CurrencyAdditionDTO currencyAdditionDTO = convertMapToDto(parameters);
        CurrencyDTO currencyDTO = currencyService.addCurrency(currencyAdditionDTO);
        ServletUtil.sendResponse(HttpServletResponse.SC_CREATED, currencyDTO, response);
    }

    //кастомный под каждый сервлет?
    private CurrencyAdditionDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            CurrencyAdditionDTO currencyAdditionDTO = ServletUtil.getJsonConverter().getMapper().convertValue(parameters, CurrencyAdditionDTO.class);
            checkCurrencySign(currencyAdditionDTO);
            return currencyAdditionDTO;
        } catch (RuntimeException e) {
            //порядок не решает, illegalArgumentException - если не те аргументы были переданы
            throw new RequiredFormFieldMissException("A required form field is missing");
        }
    }

    private void checkCurrencySign(CurrencyAdditionDTO currencyAdditionDTO) {
        if (currencyAdditionDTO.sign().length() > Currency.MAX_SIGN_SIZE) {
            throw new RuntimeException();
        }
    }
}

