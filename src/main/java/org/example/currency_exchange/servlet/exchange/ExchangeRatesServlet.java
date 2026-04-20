package org.example.currency_exchange.servlet.exchange;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.exception.RequiredFormFieldMissException;
import org.example.currency_exchange.dto.exchange.ExchangeRateAdditionDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRateDTO;
import org.example.currency_exchange.dto.exchange.service.exchange.ExchangeRateService;
import org.example.currency_exchange.util.ServletUtil;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private ExchangeRateService exchangeRateService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.exchangeRateService = (ExchangeRateService) getServletContext().getAttribute("exchangeRateService");

        if (exchangeRateService == null) {
            throw new IllegalStateException("exchangeRateService не найден");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ExchangeRateDTO> exchangeRateDTOs = exchangeRateService.getExchangeRates();
        ServletUtil.sendResponse(HttpServletResponse.SC_OK, exchangeRateDTOs, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Map<String, String> parameters = ServletUtil.getParametersFromBody(request);
            ExchangeRateAdditionDTO exchangeRateAdditionDTO = convertMapToDto(parameters);
            ExchangeRateDTO exchangeRateDTO = exchangeRateService.addExchangeRate(exchangeRateAdditionDTO);
            ServletUtil.sendResponse(HttpServletResponse.SC_CREATED, exchangeRateDTO, response);
    }

    private ExchangeRateAdditionDTO convertMapToDto(Map<String, String> parameters) throws RequiredFormFieldMissException {
        try {
            return ServletUtil.getJsonConverter().getMapper().convertValue(parameters, ExchangeRateAdditionDTO.class);
        } catch (RuntimeException e) {
            throw new RequiredFormFieldMissException("A required form field is missing");
        }
    }
}

