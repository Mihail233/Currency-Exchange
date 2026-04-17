package org.example.currency_exchange.currency.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.currency_exchange.common.BaseHttpServlet;
import org.example.currency_exchange.common.ObjectDtoMapper;
import org.example.currency_exchange.util.ServletUtil;
import org.example.currency_exchange.currency.dto.CodeDTO;
import org.example.currency_exchange.currency.dto.CurrencyDTO;
import org.example.currency_exchange.currency.mapper.CodeMapper;
import org.example.currency_exchange.currency.CurrencyService;

import java.io.IOException;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends BaseHttpServlet {
    private CurrencyService currencyService;


    private final ObjectDtoMapper<String, CodeDTO> objectDtoMapper = new CodeMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        this.currencyService = (CurrencyService) getServletContext().getAttribute("currencyService");

        if (currencyService == null) {
            throw new IllegalStateException("CurrencyService не найден");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currencyCode = ServletUtil.getParameterFromPath(request.getPathInfo());
        CodeDTO codeDTO = objectDtoMapper.objectToDto(currencyCode);
        CurrencyDTO currencyDTO = currencyService.getCurrency(codeDTO);
        sendSuccessfulResponse(HttpServletResponse.SC_OK, currencyDTO, response);
    }
}
