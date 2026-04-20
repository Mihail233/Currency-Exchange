package org.example.currency_exchange.servlet.exchange;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.exception.RequiredQueryParametersMissException;
import org.example.currency_exchange.service.exchange.ExchangeService;
import org.example.currency_exchange.dto.exchange.ExchangeResponseDTO;
import org.example.currency_exchange.dto.exchange.ExchangeRequestDTO;
import org.example.currency_exchange.util.ServletUtil;


import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ExchangeServlet", value = "/exchange")
public class ExchangeServlet extends HttpServlet {
    private ExchangeService exchangeService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.exchangeService = (ExchangeService) getServletContext().getAttribute("exchangeService");

        if (exchangeService == null) {
            throw new IllegalStateException("exchangeService не найден");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> queryParameters = ServletUtil.getParametersFromQueryParameters(request.getQueryString());
        ExchangeRequestDTO exchangeRequestDTO = convertMapToDto(queryParameters);
        ExchangeResponseDTO exchangeResponseDTO = exchangeService.makeExchange(exchangeRequestDTO);
        ServletUtil.sendResponse(HttpServletResponse.SC_OK, exchangeResponseDTO, response);
    }

    private ExchangeRequestDTO convertMapToDto(Map<String, String> queryParameters) throws RequiredQueryParametersMissException {
        try {
            return ServletUtil.getJsonConverter().getMapper().convertValue(queryParameters, ExchangeRequestDTO.class);
        } catch (RuntimeException e) {
            throw new RequiredQueryParametersMissException("The required query parameter is missing");
        }
    }
}
